package com.b4g.sid.books4geeks.Util;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.b4g.sid.books4geeks.B4GAppClass;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Sid on 04-Jan-17.
 */

public class ImageUtil {

    private ImageUtil(){

    }


    public static void saveToInternalStorage(final String bookId, final String imageUrl){
        Picasso.with(B4GAppClass.getAppContext()).load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ContextWrapper cw = new ContextWrapper(B4GAppClass.getAppContext());
                        File directory = cw.getDir("images", Context.MODE_PRIVATE);
                        File myPath = new File(directory, bookId + ".png");
                        try {
                            FileOutputStream fos = new FileOutputStream(myPath);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                            fos.close();
                        } catch (Exception e) {
                            Log.d("Sid",bookId + ":Save Failed");
                        }
                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    public static File loadImageFromStorage(String bookId) {
        ContextWrapper cw = new ContextWrapper(B4GAppClass.getAppContext());
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        return new File(directory, bookId + ".png");
    }

    public static void deleteImageFromStorage(String bookId){
        boolean isDeleted = loadImageFromStorage(bookId).delete();
        if(isDeleted){
            Log.d("Sid",bookId+" Delete Successful");
        }
        else Log.d("Sid",bookId+" Delete Unsuccessful");
    }
}
