package com.b4g.sid.books4geeks.Util;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.b4g.sid.books4geeks.B4GAppClass;

/**
 * Created by Sid on 22-Dec-16.
 */

public class VolleySingleton {

    public static VolleySingleton instance;

    public static VolleySingleton getInstance() {
        if(instance==null)  instance = new VolleySingleton();
        return instance;
    }

    public RequestQueue requestQueue;
    public ImageLoader imageLoader;

    // Constructor
    private VolleySingleton() {
        requestQueue = Volley.newRequestQueue(B4GAppClass.getAppContext());
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }
}

