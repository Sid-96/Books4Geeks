package com.b4g.sid.books4geeks.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.b4g.sid.books4geeks.R;

/**
 * Implementation of App com.b4g.sid.books4geeks.Widget functionality.
 */
public class BookSearchWidget extends AppWidgetProvider {


    public static final String WIDGET_IDS = "bookWidgetIds";
    //public static final String WIDGET_CATEGORY_POSI = "Position";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context,BookWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.books_to_read);
        rv.setRemoteAdapter(appWidgetId,R.id.book_list_widget,intent);
        rv.setEmptyView(R.id.book_list_widget,R.id.empty_view);

        Intent updateIntent = new Intent(context,BookSearchWidget.class);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        rv.setOnClickPendingIntent(R.id.widget_container, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId,rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
            Bundle extras = intent.getExtras();
            if(extras!=null){
                int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_IDS);
                AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId,R.id.book_list_widget);
            }
        }
        super.onReceive(context,intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Toast.makeText(context,"Please click on Title to refresh the widget",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

