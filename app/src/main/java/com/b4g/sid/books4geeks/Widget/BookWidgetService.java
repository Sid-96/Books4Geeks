package com.b4g.sid.books4geeks.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.b4g.sid.books4geeks.B4GAppClass;

/**
 * Created by Sid on 08-Jan-17.
 */

public class BookWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        return new BookWidgetListProvider(B4GAppClass.getAppContext(),intent);
    }
}
