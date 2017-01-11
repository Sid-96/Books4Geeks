package com.b4g.sid.books4geeks.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.data.BookColumns;
import com.b4g.sid.books4geeks.data.BookProvider;

/**
 * Created by Sid on 08-Jan-17.
 */

public class BookWidgetListProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private int appWidgetId;
    private Cursor data;

    public BookWidgetListProvider(Context context, Intent intent){
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);

    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        if(data!=null)  data.close();

        final long dataIdentityToken = Binder.clearCallingIdentity();
        String selectionquery = BookColumns.SHELF + "= " + BookColumns.SHELF_TO_READ;
        data = context.getContentResolver().query(BookProvider.Books.CONTENT_URI,new String[]{BookColumns.TITLE,BookColumns.AUTHORS,BookColumns.IMAGE_URL},selectionquery,null,null);
        Binder.restoreCallingIdentity(dataIdentityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return data==null?0:data.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.list_item_category_widget);

        if(i== AdapterView.INVALID_POSITION || data==null || !data.moveToPosition(i)){
            return null;
        }

        //Category category = Category.getCategoryList().get(i);
        //remoteViews.setTextViewText(R.id.category_name_widget,category.getDisplayName());
        remoteViews.setTextViewText(R.id.book_author,data.getString(data.getColumnIndex(BookColumns.AUTHORS)));
        remoteViews.setTextViewText(R.id.book_title,data.getString(data.getColumnIndex(BookColumns.TITLE)));
        remoteViews.setImageViewResource(R.id.book_cover,R.drawable.icon_category_love);

       final Intent fillIntent = new Intent();
        remoteViews.setOnClickFillInIntent(R.id.item_widget,fillIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
