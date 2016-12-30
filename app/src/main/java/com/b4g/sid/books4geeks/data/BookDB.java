package com.b4g.sid.books4geeks.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Sid on 30-Dec-16.
 */

@Database(version = BookDB.VERSION)
public final class BookDB {
    public static final int VERSION = 1;

    @Table(BookColumns.class) public static final String BOOKS = "books";
}
