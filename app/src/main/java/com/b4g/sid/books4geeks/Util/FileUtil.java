package com.b4g.sid.books4geeks.Util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.b4g.sid.books4geeks.data.BookColumns;
import com.b4g.sid.books4geeks.data.BookDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by Sid on 05-Jan-17.
 */

public class FileUtil {

    private FileUtil(){

    }

    public static void copyFile(File source, File destination) throws IOException {
        FileInputStream fromFile = new FileInputStream(source);
        FileOutputStream toFile = new FileOutputStream(destination);
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }

    public static boolean isValidDbFile(File db) {
        try {
            SQLiteDatabase sqlDb = SQLiteDatabase.openDatabase(db.getPath(), null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = sqlDb.query(true, BookDB.BOOKS, null, null, null, null, null, null, null);
            cursor.getColumnIndexOrThrow(BookColumns.BOOK_ID);
            cursor.getColumnIndexOrThrow(BookColumns.ISBN_10);
            cursor.getColumnIndexOrThrow(BookColumns.ISBN_13);
            cursor.getColumnIndexOrThrow(BookColumns.SHELF);
            // Close database and cursors
            sqlDb.close();
            cursor.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}


