package com.b4g.sid.books4geeks.ui.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.Model.BookDetail;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.DimensionUtil;
import com.b4g.sid.books4geeks.Util.VolleySingleton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements Toolbar.OnMenuItemClickListener {


    @BindView(R.id.toolbar)     Toolbar toolbar;
    @BindView(R.id.toolbar_layout_holder)   View toolbarLayoutHolder;
    @BindView(R.id.toolbar_title)       TextView toolbarTitle;
    @BindView(R.id.toolbar_subtitle)    TextView toolbarSubtitle;
    @BindView(R.id.detail_main_view)    View detailMainView;
    @BindView(R.id.detail_book_cover)   NetworkImageView detailBookCover;
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
    @BindView(R.id.detail_book_publication_isbn)    TextView detailBookPublicationIsbn;
    @BindView(R.id.fragment_detail_book_view)   NestedScrollView fragmentDetailBookView;
    @BindView(R.id.category_msg_holder)     View categoryMsgHolder;

    private Unbinder unbinder;
    private BookDetail bookDetail;
    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this,v);
        bookDetail = getArguments().getParcelable(B4GAppClass.BOOK_DETAIL);
        if(bookDetail==null)   {
            fragmentDetailBookView.setVisibility(View.GONE);
            if (getArguments().getBoolean(B4GAppClass.MSG_VISIBILITY, false)) {
                categoryMsgHolder.setVisibility(View.VISIBLE);
            }
            return v;
        }
        if (bookDetail.getSubtitle().length() == 0) {
            toolbarLayoutHolder.setVisibility(View.GONE);
            toolbar.setTitle(bookDetail.getTitle());
        } else {
            toolbarTitle.setText(bookDetail.getTitle());
            toolbarSubtitle.setText(bookDetail.getSubtitle());
        }
        toolbar.setOnMenuItemClickListener(this);
        toolbar.inflateMenu(R.menu.menu_detail);

        if(!DimensionUtil.isTablet()) {
            toolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_back));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        }
        bindViews();
        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VolleySingleton.getInstance().requestQueue.cancelAll(this.getClass().getName());
        unbinder.unbind();
    }


    private void bindViews(){
        toolbar.setTitle(bookDetail.getTitle());
        detailBookTitle.setText(bookDetail.getTitle());

        if(bookDetail.getImageUrl().length()==0){
            detailBookCover.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.category_temp));
        }
        else{
            detailBookCover.setImageUrl(bookDetail.getImageUrl(),VolleySingleton.getInstance().imageLoader);
        }
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
        if(bookDetail.getRating().length()==0 || bookDetail.getVoteCount().length()==0){
            detailBookRatingView.setVisibility(View.GONE);
        }
        else {
            detailBookRating.setText(bookDetail.getRating());
            detailBookVoteCount.setText(getString(R.string.detail_book_ratings, bookDetail.getRating()));
        }

        String publisher = bookDetail.getPublisher();
        String publishDate = bookDetail.getPublisherDate();
        String identifiers = bookDetail.getUniqueIdentifier();
        if (publisher.length() == 0 && publishDate.length() == 0 && identifiers.length() == 0) {
            detailBookPublisherView.setVisibility(View.GONE);
        } else {
            // Publisher
            if (publisher.length() == 0) {
                detailBookPublisherName.setVisibility(View.GONE);
            } else {
                detailBookPublisherName.setText(getString(R.string.detail_book_publication_name, publisher));
            }
            // Publish date
            if (publishDate.length() == 0) {
                detailBookPublicationDate.setVisibility(View.GONE);
            } else {
                detailBookPublicationDate.setText(getString(R.string.detail_book_publish_date, publishDate));
            }
            // Identifiers
            if (identifiers.length() == 0) {
                detailBookPublicationIsbn.setVisibility(View.GONE);
            } else {
                detailBookPublicationIsbn.setText(getString(R.string.detail_book_publication_isbn, identifiers));
            }
        }
        if(bookDetail.getDesc().length()!=0){
            detailBookDescription.setText(bookDetail.getDesc());
        }
        else detailBookDescriptionView.setVisibility(View.GONE);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.item_share){
                String shareSubject = getString(R.string.action_share_subject,bookDetail.getTitle());
                String shareText = getString(R.string.action_share_text,bookDetail.getTitle(),bookDetail.getAuthors(),bookDetail.getInfoLink());
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.action_share_title)));
                return true;
        }
        return false;
    }
}
