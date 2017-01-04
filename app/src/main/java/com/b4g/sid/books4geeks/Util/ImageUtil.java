package com.b4g.sid.books4geeks.Util;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;

import com.b4g.sid.books4geeks.B4GAppClass;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Sid on 04-Jan-17.
 */

public class ImageUtil {

    private void saveToInternalStorage(final String bookId, final Bitmap bitmap){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                ContextWrapper cw = new ContextWrapper(B4GAppClass.getAppContext());
                // Get path to /data/data/your_app_package/app_data/images
                File directory = cw.getDir("images", Context.MODE_PRIVATE);
                File myPath = new File(directory, bookId + ".png");
                // Save bitmap to storage
                try {
                    FileOutputStream fos = new FileOutputStream(myPath);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(task).start();
    }

    private File loadImageFromStorage(String bookId) {
        ContextWrapper cw = new ContextWrapper(B4GAppClass.getAppContext());
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        return new File(directory, bookId + ".png");
    }
}
