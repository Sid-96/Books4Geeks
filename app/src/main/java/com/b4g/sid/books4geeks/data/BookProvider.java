package com.b4g.sid.books4geeks.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Sid on 30-Dec-16.
 */

@ContentProvider(authority = BookProvider.AUTHORITY, database  = BookDB.class)
public class BookProvider {

    public static final String AUTHORITY = "com.b4g.sid.books4geeks.data.BookProvider";

    @TableEndpoint(table = BookDB.BOOKS) public static class Books {

        @ContentUri(
                path = "books",
                type = "vnd.android.cursor.dir/list",
                defaultSort = BookColumns.TITLE + " ASC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/books");
    }
}
