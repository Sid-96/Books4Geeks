package com.b4g.sid.books4geeks.ui.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.Model.BookDetail;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.ApiUtil;
import com.b4g.sid.books4geeks.Util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements Toolbar.OnMenuItemClickListener {


    @BindView(R.id.toolbar)     Toolbar toolbar;
    @BindView(R.id.detail_main_view)    View detailMainView;
    @BindView(R.id.detail_book_cover)   ImageView detailBookCover;
    @BindView(R.id.detail_book_title)   TextView detailBookTitle;
    @BindView(R.id.detail_book_subtitle)    TextView detailBookSubtitle;
    @BindView(R.id.detail_book_rating_view) View detailBookRatingView;
    @BindView(R.id.detail_book_rating)  TextView detailBookRating;
    @BindView(R.id.detail_book_vote_count)  TextView detailBookVoteCount;
    @BindView(R.id.detail_book_description_view)    View detailBookDescriptionView;
    @BindView(R.id.detail_book_description) TextView detailBookDescription;
    @BindView(R.id.detail_book_publisher_view)  View detailBookPublisherView;
    @BindView(R.id.detail_book_publisher_name)  TextView detailBookPublisherName;
    @BindView(R.id.detail_book_publication_date)    TextView detailBookPublicationDate;
    @BindView(R.id.fragment_detail_book_view)   NestedScrollView fragmentDetailBookView;
    @BindView(R.id.error_msg)       View errorMessage;
    @BindView(R.id.progress_circle)     View progressCircle;

    private Unbinder unbinder;
    private String isbn;
    private BookDetail bookDetail;
    private int currentState;
    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this,v);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(),R.drawable.ic_back));
        toolbar.inflateMenu(R.menu.menu_detail);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        isbn = getArguments().getString(B4GAppClass.ISBN_NO);
        if(savedInstanceState!=null && savedInstanceState.containsKey(B4GAppClass.CURRENT_STATE)){
            currentState = savedInstanceState.getInt(B4GAppClass.CURRENT_STATE);
            if(currentState==B4GAppClass.CURRENT_STATE_LOADED){
                bookDetail = savedInstanceState.getParcelable(B4GAppClass.TAG_BOOK_DETAIL_FRAGMENT);
                onDownloadSuccessful();
            }
            else if(currentState == B4GAppClass.CURRENT_STATE_LOADING){
                downloadBookDetails();
            }
            else if(currentState == B4GAppClass.CURRENT_STATE_FAILED){
                onDownloadFailed();
            }
            else
                downloadBookDetails();
        }
        else    downloadBookDetails();
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(B4GAppClass.CURRENT_STATE,currentState);
        if(bookDetail!=null){
            outState.putParcelable(B4GAppClass.TAG_BOOK_DETAIL_FRAGMENT,bookDetail);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VolleySingleton.getInstance().requestQueue.cancelAll(this.getClass().getName());
        unbinder.unbind();
    }

    private void downloadBookDetails(){
        String url = ApiUtil.getBookDetailsString(isbn);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object = response.getJSONArray("items").getJSONObject(0);
                    JSONObject volumeInfo = object.getJSONObject("volumeInfo");
                    String title = volumeInfo.getString("title");
                    JSONArray authors = volumeInfo.getJSONArray("authors");
                    StringBuilder sb = new StringBuilder();
                    for(int i=0 ; i<authors.length() ; i++){
                        sb.append(authors.getString(i)).append(", ");
                    }
                    sb.delete(sb.length()-2,sb.length());
                    String authorsName = sb.toString();
                    String description = volumeInfo.getString("description");
                    String publisher="";

                    publisher = volumeInfo.getString("publisher");

                    String imgUrl = "";

                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");

                    if(imageLinks.has("thumbnail")){
                        imgUrl = imageLinks.getString("thumbnail");
                    }
                    else if(imageLinks.has("smallThumbnail")){
                        imgUrl = imageLinks.getString("smallThumbnail");
                    }
                    String publishDate = "",avgRating = "", pageCount = "", ratingsCount = "", volumeId = "";
                    publishDate = volumeInfo.getString("publishedDate");
                    avgRating = volumeInfo.getString("averageRating");
                    pageCount = volumeInfo.getString("pageCount");
                    ratingsCount = volumeInfo.getString("ratingsCount");
                    volumeId = object.getString("id");
                    bookDetail = new BookDetail(title,authorsName,description,publisher,imgUrl,
                            publishDate,avgRating,pageCount,ratingsCount,volumeId);
                    onDownloadSuccessful();
                } catch (JSONException e) {
                    Log.d("Sid","Internet error");
                    onDownloadFailed();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onDownloadFailed();
                error.printStackTrace();
            }
        });
        objectRequest.setTag(getClass().getName());
        VolleySingleton.getInstance().requestQueue.add(objectRequest);
        currentState = B4GAppClass.CURRENT_STATE_LOADING;
    }

    private void onDownloadSuccessful(){
        progressCircle.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        fragmentDetailBookView.setVisibility(View.VISIBLE);
        if(bookDetail.getImageUrl().length()==0){
            detailBookCover.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.category_temp));
        }
        else {
            VolleySingleton.getInstance().imageLoader.get(bookDetail.getImageUrl(), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    detailBookCover.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    detailBookCover.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.category_temp));
                }
            });

        }
        bindViews();
        currentState = B4GAppClass.CURRENT_STATE_LOADED;
    }

    private void onDownloadFailed(){
        progressCircle.setVisibility(View.GONE);
        fragmentDetailBookView.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);
        currentState = B4GAppClass.CURRENT_STATE_FAILED;
    }

    private void bindViews(){
        toolbar.setTitle(bookDetail.getTitle());
        detailBookTitle.setText(bookDetail.getTitle());
        if(bookDetail.getAuthors().length()!=0 && bookDetail.getPageCount().length()!=0){
            detailBookSubtitle.setText(getString(R.string.detail_book_subtitle,bookDetail.getAuthors(),bookDetail.getPageCount()));
        }
        else if(bookDetail.getAuthors().length()!=0){
            detailBookSubtitle.setText(getString(R.string.detail_book_subtitle_author,bookDetail.getAuthors()));
        }
        else if(bookDetail.getPageCount().length()!=0){
            detailBookSubtitle.setText(getString(R.string.detail_book_subtitle_pages,bookDetail.getPageCount()));
        }
        else{
            detailBookSubtitle.setVisibility(View.GONE);
        }
        if(detailBookRating.length()==0 || detailBookVoteCount.length()==0){
            detailBookRatingView.setVisibility(View.GONE);
        }
        else {
            detailBookRating.setText(bookDetail.getRating());
            detailBookVoteCount.setText(getString(R.string.detail_book_ratings, bookDetail.getRating()));
        }

        if(bookDetail.getPublisher().length()!=0 && bookDetail.getPublisherDate().length()!=0){
            detailBookPublisherName.setText(getString(R.string.detail_book_publication_name,bookDetail.getPublisher()));
            detailBookPublicationDate.setText(getString(R.string.detail_book_publish_date,bookDetail.getPublisherDate()));
        }
        else if(bookDetail.getPublisherDate().length()!=0){
            detailBookPublisherName.setVisibility(View.GONE);
            detailBookPublicationDate.setText(getString(R.string.detail_book_publish_date,bookDetail.getPublisherDate()));
        }
        else if(bookDetail.getPublisher().length()!=0){
            detailBookPublicationDate.setVisibility(View.GONE);
            detailBookPublisherName.setText(getString(R.string.detail_book_publication_name,bookDetail.getPublisher()));
        }
        else{
            detailBookPublisherView.setVisibility(View.GONE);
        }

        if(bookDetail.getDesc().length()!=0){
            detailBookDescription.setText(bookDetail.getDesc());
        }
        else detailBookDescriptionView.setVisibility(View.GONE);
    }

    @OnClick(R.id.try_again)
    public void onTryAgainClicked(){
        errorMessage.setVisibility(View.GONE);
        fragmentDetailBookView.setVisibility(View.GONE);
        progressCircle.setVisibility(View.VISIBLE);
        downloadBookDetails();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
