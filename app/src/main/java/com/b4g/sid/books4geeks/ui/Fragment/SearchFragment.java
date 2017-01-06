package com.b4g.sid.books4geeks.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.CustomViews.ItemDecorationView;
import com.b4g.sid.books4geeks.Model.BookDetail;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.ApiUtil;
import com.b4g.sid.books4geeks.Util.DBUtil;
import com.b4g.sid.books4geeks.Util.DimensionUtil;
import com.b4g.sid.books4geeks.Util.VolleySingleton;
import com.b4g.sid.books4geeks.adapter.SearchAdapter;
import com.b4g.sid.books4geeks.ui.activity.DetailBookActivity;
import com.b4g.sid.books4geeks.ui.activity.SearchActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearchFragment extends Fragment implements SearchAdapter.OnSearchBookClickListener {

    private Unbinder unbinder;
    private String query;
    private SearchAdapter adapter;
    private int currentState;
    private BookDetail bookDetail;
    private int startIndex;
    private int totalItems;
    private Handler handler;
    private Runnable runnable;
    private GridLayoutManager gridLayoutManager;

    @BindView(R.id.toolbar)             Toolbar toolbar;
    @BindView(R.id.search_toolbar)      EditText searchBar;
    @BindView(R.id.error_msg)           View errorMessage;
    @BindView(R.id.progress_circle)     View progressCircle;
    @BindView(R.id.search_no_results)   View searchNoResults;
    @BindView(R.id.search_list)         RecyclerView searchList;
    @BindView(R.id.loading_secondary)   View loadingSecondary;
    @BindView(R.id.search_fragment_ad)  AdView adView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this,v);
        startIndex = 0;
        totalItems = 0;
        toolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    String q = searchBar.getText().toString().trim();
                    if(q.length()>0){
                        performSearchFor(q);
                        return true;
                    }
                }
                return false;
            }
        });

        /*searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    String q = searchBar.getText().toString().trim();
                    if(q.length()>0){
                        query = q;
                        startIndex = 0;
                        totalItems = 0;
                        searchList.setVisibility(View.GONE);
                        searchNoResults.setVisibility(View.GONE);
                        errorMessage.setVisibility(View.GONE);
                        progressCircle.setVisibility(View.VISIBLE);
                        adapter = null;
                        downloadSearchDetails();
                        return true;
                    }
                }
                return false;
            }
        });
        */gridLayoutManager = new GridLayoutManager(getContext(),
                DimensionUtil.getNumberOfColumns(R.dimen.book_card_width,1));
        searchList.addItemDecoration(new ItemDecorationView(getContext(),R.dimen.recycler_item_padding));
        searchList.setLayoutManager(gridLayoutManager);
        searchList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(adapter.getBestSellersList().size()>0 &&
                        gridLayoutManager.findLastVisibleItemPosition() == adapter.getBestSellersList().size()-1 &&
                        currentState!=B4GAppClass.CURRENT_STATE_LOADING && currentState!=B4GAppClass.CURRENT_STATE_LOCKED){
                    if(startIndex<totalItems){
                        loadingSecondary.setVisibility(View.VISIBLE);
                        downloadSearchDetails();
                    }
                }
            }
        });
        if(savedInstanceState!=null && savedInstanceState.containsKey(B4GAppClass.CURRENT_STATE)){
            currentState = savedInstanceState.getInt(B4GAppClass.CURRENT_STATE);
            query = savedInstanceState.getString(B4GAppClass.SEARCH_QUERY);
            startIndex = savedInstanceState.getInt(B4GAppClass.START_INDEX,0);
            totalItems = savedInstanceState.getInt(B4GAppClass.TOTAL_ITEMS,0);
            if(currentState == B4GAppClass.CURRENT_STATE_LOADED||currentState==B4GAppClass.CURRENT_STATE_LOCKED
                    ||(currentState==B4GAppClass.CURRENT_STATE_LOADING && savedInstanceState.containsKey(B4GAppClass.SEARCH_LIST))){
                ArrayList<BookDetail> bestSellersList = savedInstanceState.getParcelableArrayList(B4GAppClass.SEARCH_LIST);
                adapter = new SearchAdapter(this);
                adapter.setBestSellersList(bestSellersList);
                searchList.swapAdapter(adapter,true);
                onDownloadSuccessful();
                if(currentState==B4GAppClass.CURRENT_STATE_LOADING){
                    loadingSecondary.setVisibility(View.VISIBLE);
                    downloadSearchDetails();
                }
            }
            else if(currentState == B4GAppClass.CURRENT_STATE_LOADING){
                progressCircle.setVisibility(View.VISIBLE);
                downloadSearchDetails();
            }
            else if(currentState == B4GAppClass.CURRENT_STATE_FAILED){
                onDownloadFailed();
            }
        }
        if (!DimensionUtil.isTablet()) {

            runnable = new Runnable() {
                @Override
                public void run() {
                    AdRequest adRequest = new AdRequest.Builder().addTestDevice(getString(R.string.ad_test_device_id)).build();
                    adView.loadAd(adRequest);
                }
            };
            handler = new Handler();
            handler.postDelayed(runnable,1000);
        }
        else {
            adView.setVisibility(View.GONE);
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VolleySingleton.getInstance().requestQueue.cancelAll(getClass().getName());
        if(handler!=null)   handler.removeCallbacks(runnable);
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(B4GAppClass.CURRENT_STATE,currentState);
        outState.putString(B4GAppClass.SEARCH_QUERY,query);
        outState.putInt(B4GAppClass.TOTAL_ITEMS,totalItems);
        outState.putInt(B4GAppClass.START_INDEX,startIndex);
        if(adapter!=null){
            outState.putParcelableArrayList(B4GAppClass.SEARCH_LIST,adapter.getBestSellersList());
        }
    }

    private void downloadSearchDetails(){
        if(adapter==null){
            adapter = new SearchAdapter(this);
            searchList.swapAdapter(adapter,true);
        }
        String url = ApiUtil.getBookSearchString(query,startIndex);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.has("items")){
                                JSONArray object = response.getJSONArray("items");
                                totalItems = response.getInt("totalItems");
                                for(int i=0 ; i<object.length() ; i++){
                                    JSONObject bookObject = object.getJSONObject(i);
                                    JSONObject volumeInfo = bookObject.getJSONObject("volumeInfo");
                                    String title = volumeInfo.getString("title");
                                    String subtitle="";
                                    if(volumeInfo.has("subtitle"))
                                        subtitle = volumeInfo.getString("subtitle");
                                    JSONArray authors = volumeInfo.getJSONArray("authors");
                                    StringBuilder sb = new StringBuilder();
                                    for(int j=0 ; j<authors.length() ; j++){
                                        sb.append(authors.getString(j)).append(", ");
                                    }
                                    sb.delete(sb.length()-2,sb.length());
                                    String authorsName = sb.toString();
                                    String imgUrl = "",isbn10="",isbn13="";

                                    if(volumeInfo.has("industryIdentifiers")){
                                        JSONArray identifierArray = volumeInfo.getJSONArray("industryIdentifiers");
                                        for(int j=0 ;  j<identifierArray.length() ; j++){
                                            JSONObject identifier = identifierArray.getJSONObject(j);
                                            if(identifier.getString("type").equals("ISBN_10"))  isbn10 = identifier.getString("identifier");
                                            if(identifier.getString("type").equals("ISBN_13"))  isbn13 = identifier.getString("identifier");
                                        }
                                    }

                                    if(volumeInfo.has("imageLinks")){
                                        JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");

                                        if(imageLinks.has("thumbnail")){
                                            imgUrl = imageLinks.getString("thumbnail");
                                        }
                                        else if(imageLinks.has("smallThumbnail")){
                                            imgUrl = imageLinks.getString("smallThumbnail");
                                        }

                                    }

                                    String description = "";
                                    if(volumeInfo.has("description"))
                                        volumeInfo.getString("description");
                                    String infoLink="",publisher="";
                                    publisher = volumeInfo.getString("publisher");
                                    String publishDate = "",avgRating = "", pageCount = "", ratingsCount = "", uniqueId = "";
                                    if(volumeInfo.has("publishedDate"))
                                        publishDate = volumeInfo.getString("publishedDate");
                                    if(volumeInfo.has("averageRating"))
                                        avgRating = volumeInfo.getString("averageRating");
                                    if(volumeInfo.has("infoLink"))
                                        infoLink= volumeInfo.getString("infoLink");
                                    if(volumeInfo.has("pageCount"))
                                        pageCount = volumeInfo.getString("pageCount");
                                    if(volumeInfo.has("ratingsCount"))
                                        ratingsCount = volumeInfo.getString("ratingsCount");
                                    uniqueId = "gbid_"+bookObject.getString("id");
                                    bookDetail = new BookDetail(title, subtitle, authorsName,description,publisher, isbn10, isbn13, imgUrl,
                                            infoLink, publishDate, pageCount, ratingsCount, uniqueId, avgRating);
                                    adapter.addToList(bookDetail);
                                }

                            }
                            startIndex += 11;
                            Log.d("Sid","Download Success");
                            onDownloadSuccessful();
                        } catch (JSONException e) {
                            onDownloadFailed();
                            e.printStackTrace();
                            Log.d("Sid","Download Fail"+e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onDownloadFailed();
            }
        });
        request.setTag(getClass().getName());
        VolleySingleton.getInstance().requestQueue.add(request);
        currentState = B4GAppClass.CURRENT_STATE_LOADING;
    }


    private void onDownloadFailed(){
        progressCircle.setVisibility(View.GONE);
        loadingSecondary.setVisibility(View.GONE);
        searchNoResults.setVisibility(View.GONE);
        if(startIndex==0){
            searchList.setVisibility(View.GONE);
            errorMessage.setVisibility(View.VISIBLE);
            currentState = B4GAppClass.CURRENT_STATE_FAILED;
        }
        else {
            errorMessage.setVisibility(View.GONE);
            searchList.setVisibility(View.VISIBLE);
            currentState = B4GAppClass.CURRENT_STATE_LOCKED;
        }
    }

    private void onDownloadSuccessful(){
        BookDetail bookDetail;
        progressCircle.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        if(adapter.getBestSellersList().size()>0) {
            searchNoResults.setVisibility(View.GONE);
            searchList.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
            bookDetail = adapter.getBestSellersList().get(0);
        }
        else{
            searchList.setVisibility(View.GONE);
            searchNoResults.setVisibility(View.VISIBLE);
            bookDetail = null;
        }
        if(DimensionUtil.isTablet()&&startIndex==11){
            ((SearchActivity)getActivity()).loadDetailFragmentforTablet(bookDetail);
        }
        currentState = B4GAppClass.CURRENT_STATE_LOADED;
    }

    public void performSearchFor(String searchQuery){
        query = searchQuery;
        startIndex = 0;
        totalItems = 0;
        searchBar.setText(query);
        searchList.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        searchNoResults.setVisibility(View.GONE);
        progressCircle.setVisibility(View.VISIBLE);
        adapter = null;
        downloadSearchDetails();
    }

    @OnClick(R.id.try_again)
    public void onTryAgainClicked(){
        errorMessage.setVisibility(View.GONE);
        searchList.setVisibility(View.GONE);
        searchNoResults.setVisibility(View.GONE);
        progressCircle.setVisibility(View.VISIBLE);
        adapter = null;
        downloadSearchDetails();
    }

    @Override
    public void onBookClicked(int position) {
        if(DimensionUtil.isTablet()){
            BookDetail bookDetail = adapter.getBestSellersList().get(position);
            ((SearchActivity)getActivity()).loadDetailFragmentforTablet(bookDetail);
        }
        Intent intent = new Intent(getContext(), DetailBookActivity.class);
        intent.putExtra(B4GAppClass.BOOK_DETAIL,adapter.getBestSellersList().get(position));
        startActivity(intent);
    }

    @Override
    public void onBookMenuClicked(int position, View view) {
        BookDetail bookDetail = adapter.getBestSellersList().get(position);
        int shelf = DBUtil.getCurrentShelf(bookDetail);
        DBUtil.getPopupMenu(getActivity(),bookDetail,shelf,view).show();
    }
}
