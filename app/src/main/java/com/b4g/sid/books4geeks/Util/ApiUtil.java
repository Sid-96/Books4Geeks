package com.b4g.sid.books4geeks.Util;

import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.R;

/**
 * Created by Sid on 24-Dec-16.
 */

public class ApiUtil {

    private ApiUtil(){

    }

    private static String getGoogleBooksApiKey(){
        return B4GAppClass.getAppContext().getResources().getString(R.string.google_books_api_key);
    }

    private static String getNytApiKey(){
        return B4GAppClass.getAppContext().getResources().getString(R.string.nyt_api_key);
    }

    public static String getBestSellerList(String listName){
        return "https://api.nytimes.com/svc/books/v3/lists//"+listName+".json?api-key=" + getNytApiKey();
    }

    public static String getBookDetailsString(String isbn){
        return "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn + "&key=" + getGoogleBooksApiKey();
    }
}
