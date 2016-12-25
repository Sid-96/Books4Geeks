package com.b4g.sid.books4geeks.ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.Model.BestSeller;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.ApiUtil;
import com.b4g.sid.books4geeks.Util.DimensionUtil;
import com.b4g.sid.books4geeks.Util.VolleySingleton;
import com.b4g.sid.books4geeks.ui.adapter.BestSellerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BestSellerFragment extends Fragment implements BestSellerAdapter.OnBestSellerClickListener {

    @BindView(R.id.best_seller_list)    RecyclerView recyclerView;
    Unbinder unbinder;

    private String listName;
    GridLayoutManager layoutManager;
    BestSellerAdapter bestSellerAdapter;

    public BestSellerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_best_seller, container, false);
        unbinder = ButterKnife.bind(this,v);
        listName = getArguments().getString(B4GAppClass.LIST_NAME);
        layoutManager = new GridLayoutManager(getContext(), DimensionUtil.getNumberOfColumns(R.dimen.book_card_width,1));
        bestSellerAdapter = new BestSellerAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(bestSellerAdapter);
        recyclerView.setLayoutManager(layoutManager);

        downloadBestSellerList();
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroy();
        VolleySingleton.getInstance().requestQueue.cancelAll(this.getClass().getName());
        unbinder.unbind();
    }

    private void downloadBestSellerList(){
        if(bestSellerAdapter==null){
            bestSellerAdapter = new BestSellerAdapter(this);
            recyclerView.setAdapter(bestSellerAdapter);
        }
        String urlToDownload = ApiUtil.getBestSellerList(listName);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlToDownload, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray books = response.getJSONObject("results").getJSONArray("books");
                    for(int i=0 ; i<books.length() ; i++){
                        JSONObject book = books.getJSONObject(i);
                        String title = book.getString("title");
                        String author = book.getString("author");
                        String description = book.getString("description");
                        String isbn10 = book.getString("primary_isbn10");
                        String isbn13 = book.getString("primary_isbn13");
                        String urlImage = book.getString("book_image");
                        BestSeller bestSeller = new BestSeller(title,author,description,isbn10,isbn13,urlImage);
                        bestSellerAdapter.addToList(bestSeller);
                    }
                    onDownloadSuccessful();
                } catch (JSONException e) {
                    e.printStackTrace();
                    onDownloadFailed();
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
    }

    private void onDownloadSuccessful(){
        //TODO handle download success
        bestSellerAdapter.notifyDataSetChanged();
    }

    private void onDownloadFailed(){
        //TODO handle download fail
    }

    @Override
    public void onBestSellerClicked(int position) {
        //TODO Navigate to detail screen.
    }
}
