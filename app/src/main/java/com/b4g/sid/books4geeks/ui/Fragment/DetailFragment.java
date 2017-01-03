package com.b4g.sid.books4geeks.ui.Fragment;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import com.b4g.sid.books4geeks.data.BookColumns;
import com.b4g.sid.books4geeks.data.BookProvider;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.fab_menu)                FloatingActionMenu fabMenu;
    @BindView(R.id.fab_to_read)             FloatingActionButton fabToRead;
    @BindView(R.id.fab_reading)             FloatingActionButton fabReading;
    @BindView(R.id.fab_finished)            FloatingActionButton fabFinished;


    private Unbinder unbinder;
    private BookDetail bookDetail;
    private int shelf;
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
            fabMenu.setVisibility(View.GONE);
            fragmentDetailBookView.setVisibility(View.GONE);
            if (getArguments().getBoolean(B4GAppClass.MSG_VISIBILITY, false)) {
                categoryMsgHolder.setVisibility(View.VISIBLE);
            }
            return v;
        }
        updateFabMenu();
        /*fragmentDetailBookView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (oldScrollY < scrollY) {
                    fabMenu.hideMenuButton(true);
                } else {
                    fabMenu.showMenuButton(true);
                }
            }
        });
        */if (bookDetail.getSubtitle().length() == 0) {
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

    private void updateFabMenu(){
        String selectionQuery = BookColumns.BOOK_ID + "= '" + bookDetail.getUniqueId() + "'";
        Cursor data = getContext().getContentResolver().query(BookProvider.Books.CONTENT_URI, new String[]{BookColumns.SHELF},selectionQuery,null,null);

        if(data!=null && data.getCount()>0){
            data.moveToFirst();
            shelf = data.getInt(data.getColumnIndex(BookColumns.SHELF));
            if(shelf == BookColumns.SHELF_TO_READ) {
                fabToRead.setVisibility(View.VISIBLE);
                fabReading.setVisibility(View.VISIBLE);
                fabFinished.setVisibility(View.VISIBLE);
                fabToRead.setLabelText(getString(R.string.detail_fab_to_read_remove));
                fabReading.setLabelText(getString(R.string.detail_fab_reading));
                fabFinished.setLabelText(getString(R.string.detail_fab_finished));
            }

            else if(shelf == BookColumns.SHELF_READING) {
                fabToRead.setVisibility(View.GONE);
                fabReading.setVisibility(View.VISIBLE);
                fabFinished.setVisibility(View.VISIBLE);
                fabReading.setLabelText(getString(R.string.detail_fab_reading_remove));
                fabFinished.setLabelText(getString(R.string.detail_fab_finished));

            }

            else if(shelf == BookColumns.SHELF_FINISHED) {
                fabToRead.setVisibility(View.GONE);
                fabReading.setVisibility(View.GONE);
                fabFinished.setVisibility(View.VISIBLE);
                fabFinished.setLabelText(getString(R.string.detail_fab_finished_remove));
            }
            data.close();
        }
        else{
            shelf = BookColumns.SHELF_NONE;
            fabToRead.setVisibility(View.VISIBLE);
            fabReading.setVisibility(View.VISIBLE);
            fabFinished.setVisibility(View.VISIBLE);
            fabToRead.setLabelText(getString(R.string.detail_fab_to_read));
            fabReading.setLabelText(getString(R.string.detail_fab_reading));
            fabFinished.setLabelText(getString(R.string.detail_fab_finished));
        }
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


    private ContentValues getContentValues(int shelf) {
        ContentValues values = new ContentValues();
        values.put(BookColumns.BOOK_ID, bookDetail.getUniqueId());
        values.put(BookColumns.TITLE, bookDetail.getTitle());
        values.put(BookColumns.SUBTITLE, bookDetail.getSubtitle());
        values.put(BookColumns.DESCRIPTION, bookDetail.getDesc());
        values.put(BookColumns.AUTHORS, bookDetail.getAuthors());
        values.put(BookColumns.PUBLISHER, bookDetail.getPublisher());
        values.put(BookColumns.PUBLISH_DATE, bookDetail.getPublisherDate());
        values.put(BookColumns.ISBN_10, bookDetail.getIsbn10());
        values.put(BookColumns.ISBN_13, bookDetail.getIsbn13());
        values.put(BookColumns.PAGE_COUNT, bookDetail.getPageCount());
        values.put(BookColumns.AVG_RATING, bookDetail.getRating());
        values.put(BookColumns.VOTE_COUNT, bookDetail.getVoteCount());
        values.put(BookColumns.IMAGE_URL, bookDetail.getImageUrl());
        values.put(BookColumns.INFO_LINK, bookDetail.getInfoLink());
        values.put(BookColumns.SHELF, shelf);
        return values;
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
    @OnClick(R.id.fab_to_read)
    public void onToReadButtonClicked() {
        if (shelf == BookColumns.SHELF_TO_READ) {
            // Remove from "To Read"
            getContext().getContentResolver().
                    delete(BookProvider.Books.CONTENT_URI,
                            BookColumns.BOOK_ID + " = '" + bookDetail.getUniqueId() + "'",
                            null);
        } else {
            // Insert into "To Read"
            getContext().getContentResolver().
                    insert(BookProvider.Books.CONTENT_URI,
                            getContentValues(BookColumns.SHELF_TO_READ));
        }
        updateFabMenu();
    }

    @OnClick(R.id.fab_reading)
    public void onReadingButtonClicked() {
        if (shelf == BookColumns.SHELF_TO_READ) {
            // Move from "To Read" to "Reading"
            ContentValues values = new ContentValues();
            values.put(BookColumns.SHELF, BookColumns.SHELF_READING);
            getContext().getContentResolver().
                    update(BookProvider.Books.CONTENT_URI, values,
                            BookColumns.BOOK_ID + " = '" + bookDetail.getUniqueId() + "'",
                            new String[]{});
        } else if (shelf == BookColumns.SHELF_READING) {
            // Remove from "Reading"
            getContext().getContentResolver().
                    delete(BookProvider.Books.CONTENT_URI,
                            BookColumns.BOOK_ID + " = '" + bookDetail.getUniqueId() + "'",
                            null);
        } else {
            // Insert into "Reading"
            getContext().getContentResolver().
                    insert(BookProvider.Books.CONTENT_URI,
                            getContentValues(BookColumns.SHELF_READING));
        }
        updateFabMenu();
    }
    @OnClick(R.id.fab_finished)
    public void onFinishedButtonClicked() {
        if (shelf == BookColumns.SHELF_TO_READ || shelf == BookColumns.SHELF_READING) {
            // Move from "To Read" or "Reading" to "Finished"
            ContentValues values = new ContentValues();
            values.put(BookColumns.SHELF, BookColumns.SHELF_FINISHED);
            getContext().getContentResolver().
                    update(BookProvider.Books.CONTENT_URI, values,
                            BookColumns.BOOK_ID + " = '" + bookDetail.getUniqueId() + "'",
                            new String[]{});
        } else if (shelf == BookColumns.SHELF_FINISHED) {
            // Remove from "Finished"
            getContext().getContentResolver().
                    delete(BookProvider.Books.CONTENT_URI,
                            BookColumns.BOOK_ID + " = '" + bookDetail.getUniqueId() + "'",
                            null);
        } else {
            // Insert into "Finished"
            getContext().getContentResolver().
                    insert(BookProvider.Books.CONTENT_URI,
                            getContentValues(BookColumns.SHELF_FINISHED));
        }
        updateFabMenu();
    }

}
