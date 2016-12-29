package com.b4g.sid.books4geeks.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.Model.BestSeller;
import com.b4g.sid.books4geeks.Model.Category;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.ApiUtil;
import com.b4g.sid.books4geeks.Util.DimensionUtil;
import com.b4g.sid.books4geeks.Util.VolleySingleton;
import com.b4g.sid.books4geeks.ui.CustomViews.ItemDecorationView;
import com.b4g.sid.books4geeks.ui.activity.DetailBookActivity;
import com.b4g.sid.books4geeks.ui.adapter.BestSellerAdapter;
import com.b4g.sid.books4geeks.ui.adapter.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BestSellerFragment extends Fragment implements BestSellerAdapter.OnBestSellerClickListener,CategoryAdapter.OnCategoryItemClickListener {

    @BindView(R.id.best_seller_list)    RecyclerView bestSellerList;
    @BindView(R.id.error_msg)           View errorMessage;
    @BindView(R.id.progress_circle)     View progressCircle;
    @BindView(R.id.category_list)       RecyclerView categoryList;
    Unbinder unbinder;
    public boolean back;
    private String listName;
    GridLayoutManager layoutManager;
    BestSellerAdapter bestSellerAdapter;
    private int currentState;
    public BestSellerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_best_seller, container, false);
        unbinder = ButterKnife.bind(this,v);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        CategoryAdapter categoryAdapter = new CategoryAdapter(this);
        categoryList.setHasFixedSize(true);
        categoryList.setLayoutManager(linearLayoutManager);
        categoryList.setAdapter(categoryAdapter);

        if(savedInstanceState!=null && savedInstanceState.containsKey(B4GAppClass.CURRENT_STATE)){
            currentState = savedInstanceState.getInt(B4GAppClass.CURRENT_STATE);
            listName = savedInstanceState.getString(B4GAppClass.LIST_NAME);
            back = savedInstanceState.getBoolean(B4GAppClass.BESTSELLER_BACK);
            if(currentState == B4GAppClass.CURRENT_STATE_LOADED){
                ArrayList<BestSeller> bestSellers = savedInstanceState.getParcelableArrayList(B4GAppClass.BESTSELLER_LIST);
                layoutManager = new GridLayoutManager(getContext(), DimensionUtil.getNumberOfColumns(R.dimen.book_card_width,1));
                bestSellerAdapter = new BestSellerAdapter(bestSellers,this);
                bestSellerList.setHasFixedSize(true);
                bestSellerList.addItemDecoration(new ItemDecorationView(getContext(),R.dimen.recycler_item_padding));
                bestSellerList.setAdapter(bestSellerAdapter);
                bestSellerList.setLayoutManager(layoutManager);
                onDownloadSuccessful();
            }

            else if (currentState == B4GAppClass.CURRENT_STATE_LOADING){
                navigateToBestSellers();
            }
            else if(currentState==B4GAppClass.CURRENT_STATE_FAILED) {
                onDownloadFailed();
            }
        }

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(B4GAppClass.CURRENT_STATE,currentState);
        outState.putString(B4GAppClass.LIST_NAME,listName);
        outState.putBoolean(B4GAppClass.BESTSELLER_BACK,back);
        if(bestSellerAdapter!=null){
            outState.putParcelableArrayList(B4GAppClass.BESTSELLER_LIST,bestSellerAdapter.getBestSellersList());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroy();
        VolleySingleton.getInstance().requestQueue.cancelAll(this.getClass().getName());
        unbinder.unbind();
    }


    public void navigateToBestSellers(){
        back = true;
        categoryList.setVisibility(View.GONE);
        bestSellerList.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        progressCircle.setVisibility(View.VISIBLE);
        layoutManager = new GridLayoutManager(getContext(), DimensionUtil.getNumberOfColumns(R.dimen.book_card_width,1));
        bestSellerAdapter = new BestSellerAdapter(this);
        bestSellerList.setHasFixedSize(true);
        bestSellerList.addItemDecoration(new ItemDecorationView(getContext(),R.dimen.recycler_item_padding));
        bestSellerList.setAdapter(bestSellerAdapter);
        bestSellerList.setLayoutManager(layoutManager);
        downloadBestSellerList();
    }

    public void navigateToCategories(){

        ((DrawerFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_main)).toolbar.setTitle(getString(R.string.drawer_bestsellers));
        back = false;
        bestSellerAdapter = null;
        layoutManager = null;
        VolleySingleton.getInstance().requestQueue.cancelAll(this.getClass().getName());
        bestSellerList.setVisibility(View.GONE);
        progressCircle.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        categoryList.setVisibility(View.VISIBLE);

    }


    private void downloadBestSellerList(){
        if(bestSellerAdapter==null){
            layoutManager = new GridLayoutManager(getContext(), DimensionUtil.getNumberOfColumns(R.dimen.book_card_width,1));
            bestSellerAdapter = new BestSellerAdapter(this);
            bestSellerList.setHasFixedSize(true);
            bestSellerList.addItemDecoration(new ItemDecorationView(getContext(),R.dimen.recycler_item_padding));
            bestSellerList.setAdapter(bestSellerAdapter);
            bestSellerList.setLayoutManager(layoutManager);
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
                        String itemUrl = book.getString("amazon_product_url");
                        BestSeller bestSeller = new BestSeller(title,author,description,isbn10,isbn13,urlImage,itemUrl);
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
        request.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().requestQueue.add(request);
        currentState = B4GAppClass.CURRENT_STATE_LOADING;
    }

    private void onDownloadSuccessful(){
        categoryList.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        progressCircle.setVisibility(View.GONE);
        bestSellerList.setVisibility(View.VISIBLE);
        bestSellerAdapter.notifyDataSetChanged();
        currentState = B4GAppClass.CURRENT_STATE_LOADED;
    }

    private void onDownloadFailed(){
        categoryList.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);
        progressCircle.setVisibility(View.GONE);
        bestSellerList.setVisibility(View.GONE);
        currentState = B4GAppClass.CURRENT_STATE_FAILED;
    }

    @OnClick(R.id.try_again)
    public void onTryAgainClicked(){
        errorMessage.setVisibility(View.GONE);
        bestSellerList.setVisibility(View.GONE);
        progressCircle.setVisibility(View.VISIBLE);
        bestSellerAdapter = null;
        downloadBestSellerList();
    }

    @Override
    public void onBestSellerClicked(int position) {
        Intent intent = new Intent(getContext(),DetailBookActivity.class);
        intent.putExtra(B4GAppClass.ISBN_NO,bestSellerAdapter.getBestSellersList().get(position).getIsbn10());
        startActivity(intent);
    }

    @Override
    public void onCategoryItemClicked(int position) {
        listName = Category.getCategoryList().get(position).getListName();
        String displayName = Category.getCategoryList().get(position).getDisplayName();
        ((DrawerFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_main)).toolbar.setTitle(displayName);
        navigateToBestSellers();
    }


}
