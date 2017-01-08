package com.b4g.sid.books4geeks.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.ui.activity.MainActivity;

/**
 * Implementation of App com.b4g.sid.books4geeks.Widget functionality.
 */
public class BookSearchWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context,BookWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.book_search_widget);
        rv.setRemoteAdapter(appWidgetId,R.id.book_list_widget,intent);

        Intent categoryIntent = new Intent(context,BookWidgetService.class);
        categoryIntent.setAction(B4GAppClass.WIDGET_CATEGORY_ACTION);
        categoryIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        categoryIntent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,categoryIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setPendingIntentTemplate(appWidgetId,pendingIntent);
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
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if(intent.getAction()==B4GAppClass.WIDGET_CATEGORY_ACTION){
            int position = intent.getIntExtra(B4GAppClass.WIDGET_CATEGORY_POSITION,0);
            Intent mainIntent = new Intent(context, MainActivity.class);
            mainIntent.putExtra(B4GAppClass.WIDGET_CATEGORY_POSITION,position);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainIntent);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

