package com.b4g.sid.books4geeks.Util;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.Model.BookDetail;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.data.BookColumns;
import com.b4g.sid.books4geeks.data.BookProvider;

/**
 * Created by Sid on 04-Jan-17.
 */

public class DBUtil {

    private DBUtil(){

    }

    private static ContentValues getContentValues(BookDetail bookDetail, int shelf) {
        ContentValues values = new ContentValues();
        values.put(BookColumns.BOOK_ID, bookDetail.getUniqueIdentifier());
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


    public static int getCurrentShelf(BookDetail bookDetail){

        String selectionQuery = BookColumns.BOOK_ID + "= '" + bookDetail.getUniqueIdentifier() + "'";
        Cursor data = B4GAppClass.getAppContext().getContentResolver().query(BookProvider.Books.CONTENT_URI, new String[]{BookColumns.SHELF},selectionQuery,null,null);
        if(data!=null && data.getCount()>0){
            data.moveToFirst();
            int currentShelf = data.getInt(data.getColumnIndex(BookColumns.SHELF));
            data.close();
            return currentShelf;
        }
        else
            return 0;
    }

    public static PopupMenu getPopupMenu(Activity activity, final BookDetail book, final int currentShelf, View view) {
        PopupMenu popupMenu = new PopupMenu(activity, view);
        popupMenu.inflate(R.menu.menu_popup);
        switch (currentShelf) {
            case BookColumns.SHELF_TO_READ:
                popupMenu.getMenu().getItem(0).setTitle(activity.getString(R.string.detail_fab_to_read_remove));
                popupMenu.getMenu().getItem(1).setTitle(activity.getString(R.string.detail_fab_reading));
                popupMenu.getMenu().getItem(2).setTitle(activity.getString(R.string.detail_fab_finished));
                break;

            case BookColumns.SHELF_READING:
                popupMenu.getMenu().getItem(0).setVisible(false);
                popupMenu.getMenu().getItem(1).setTitle(activity.getString(R.string.detail_fab_reading_remove));
                popupMenu.getMenu().getItem(2).setTitle(activity.getString(R.string.detail_fab_finished));
                break;

            case BookColumns.SHELF_FINISHED:
                popupMenu.getMenu().getItem(0).setVisible(false);
                popupMenu.getMenu().getItem(1).setVisible(false);
                popupMenu.getMenu().getItem(2).setTitle(activity.getString(R.string.detail_fab_finished_remove));
                break;
        }
        // Set click listener for popup menu
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.item_to_read:
                        onToReadButtonClicked(book, currentShelf);
                        return true;

                    case R.id.item_reading:
                        onReadingButtonClicked(book, currentShelf);
                        return true;

                    case R.id.item_completed:
                        onFinishedButtonClicked(book, currentShelf);
                        return true;

                    default:
                        return false;
                }
            }
        });
        return popupMenu;
    }

    public static void onToReadButtonClicked(BookDetail bookDetail , int shelf) {
        if (shelf == BookColumns.SHELF_TO_READ) {
            B4GAppClass.getAppContext().getContentResolver().
                    delete(BookProvider.Books.CONTENT_URI,
                            BookColumns.BOOK_ID + " = '" + bookDetail.getUniqueIdentifier() + "'",
                            null);
            ImageUtil.deleteImageFromStorage(bookDetail.getUniqueIdentifier());
        } else {
            B4GAppClass.getAppContext().getContentResolver().
                    insert(BookProvider.Books.CONTENT_URI,
                            getContentValues(bookDetail,BookColumns.SHELF_TO_READ));
            ImageUtil.saveToInternalStorage(bookDetail.getUniqueIdentifier(),bookDetail.getImageUrl());
        }
    }

    public static void onReadingButtonClicked(BookDetail bookDetail, int shelf) {
        if (shelf == BookColumns.SHELF_TO_READ) {
            ContentValues values = new ContentValues();
            values.put(BookColumns.SHELF, BookColumns.SHELF_READING);
            B4GAppClass.getAppContext().getContentResolver().
                    update(BookProvider.Books.CONTENT_URI, values,
                            BookColumns.BOOK_ID + " = '" + bookDetail.getUniqueIdentifier() + "'",
                            new String[]{});
        } else if (shelf == BookColumns.SHELF_READING) {
            B4GAppClass.getAppContext().getContentResolver().
                    delete(BookProvider.Books.CONTENT_URI,
                            BookColumns.BOOK_ID + " = '" + bookDetail.getUniqueIdentifier() + "'",
                            null);
            ImageUtil.deleteImageFromStorage(bookDetail.getUniqueIdentifier());
        } else {
            B4GAppClass.getAppContext().getContentResolver().
                    insert(BookProvider.Books.CONTENT_URI,
                            getContentValues(bookDetail,BookColumns.SHELF_READING));
            ImageUtil.saveToInternalStorage(bookDetail.getUniqueIdentifier(),bookDetail.getImageUrl());

        }
    }

    public static void onFinishedButtonClicked(BookDetail bookDetail, int shelf) {
        if (shelf == BookColumns.SHELF_TO_READ || shelf == BookColumns.SHELF_READING) {
            ContentValues values = new ContentValues();
            values.put(BookColumns.SHELF, BookColumns.SHELF_FINISHED);
            B4GAppClass.getAppContext().getContentResolver().
                    update(BookProvider.Books.CONTENT_URI, values,
                            BookColumns.BOOK_ID + " = '" + bookDetail.getUniqueIdentifier() + "'",
                            new String[]{});
        } else if (shelf == BookColumns.SHELF_FINISHED) {
            B4GAppClass.getAppContext().getContentResolver().
                    delete(BookProvider.Books.CONTENT_URI,
                            BookColumns.BOOK_ID + " = '" + bookDetail.getUniqueIdentifier() + "'",
                            null);
            ImageUtil.deleteImageFromStorage(bookDetail.getUniqueIdentifier());
        } else {
            B4GAppClass.getAppContext().getContentResolver().
                    insert(BookProvider.Books.CONTENT_URI,
                            getContentValues(bookDetail,BookColumns.SHELF_FINISHED));
            ImageUtil.saveToInternalStorage(bookDetail.getUniqueIdentifier(),bookDetail.getImageUrl());

        }
    }

}
