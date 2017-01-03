package com.b4g.sid.books4geeks.adapter;

import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.Model.BookDetail;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.VolleySingleton;
import com.b4g.sid.books4geeks.data.BookColumns;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sid on 03-Jan-17.
 */

public class BookCursorAdapter extends CursorRecyclerViewAdapter<BookCursorAdapter.BookCursorViewHolder> {

    private OnBookClickListener onBookClickListener;

    public BookCursorAdapter(OnBookClickListener onBookClickListener, Cursor cursor) {
        super(B4GAppClass.getAppContext(), cursor);
        this.onBookClickListener = onBookClickListener;
    }

    @Override
    public void onBindViewHolder(BookCursorViewHolder viewHolder, Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(BookColumns.TITLE));
        String subtitle = cursor.getString(cursor.getColumnIndex(BookColumns.SUBTITLE));
        String authors = cursor.getString(cursor.getColumnIndex(BookColumns.AUTHORS));
        String desc = cursor.getString(cursor.getColumnIndex(BookColumns.DESCRIPTION));
        String publisher = cursor.getString(cursor.getColumnIndex(BookColumns.PUBLISHER));
        String isbn10 = cursor.getString(cursor.getColumnIndex(BookColumns.ISBN_10));
        String isbn13 = cursor.getString(cursor.getColumnIndex(BookColumns.ISBN_13));
        String imageUrl = cursor.getString(cursor.getColumnIndex(BookColumns.IMAGE_URL));
        String infoLink = cursor.getString(cursor.getColumnIndex(BookColumns.INFO_LINK));
        String publishDate = cursor.getString(cursor.getColumnIndex(BookColumns.PUBLISH_DATE));
        String pageCount = cursor.getString(cursor.getColumnIndex(BookColumns.PAGE_COUNT));
        String voteCount = cursor.getString(cursor.getColumnIndex(BookColumns.VOTE_COUNT));
        String uniqueId = cursor.getString(cursor.getColumnIndex(BookColumns.BOOK_ID));
        String avgRating = cursor.getString(cursor.getColumnIndex(BookColumns.AVG_RATING));

        BookDetail bookDetail = new BookDetail(title,subtitle,authors,desc,publisher,isbn10,isbn13,imageUrl,
                infoLink,publishDate,pageCount,voteCount,uniqueId,avgRating);

        if(bookDetail.getImageUrl().length()==0){
            viewHolder.bookImage.setImageDrawable(ContextCompat.getDrawable(B4GAppClass.getAppContext(),R.drawable.category_temp));
        }
        else {
            viewHolder.bookImage.setImageUrl(bookDetail.getImageUrl(), VolleySingleton.getInstance().imageLoader);
        }
        viewHolder.bookTitle.setText(bookDetail.getTitle());
        viewHolder.bookAuthor.setText(bookDetail.getAuthors());
    }

    @Override
    public BookCursorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bestseller,parent,false);
        return new BookCursorViewHolder(v,onBookClickListener);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public class BookCursorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.book_card)       CardView cardView;
        @BindView(R.id.book_title)      TextView bookTitle;
        @BindView(R.id.book_author)     TextView bookAuthor;
        @BindView(R.id.book_image)      NetworkImageView bookImage;


        public BookCursorViewHolder(View itemView, final OnBookClickListener onBookClickListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBookClickListener.onBookClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface OnBookClickListener{
        void onBookClicked(final int position);
    }
}
