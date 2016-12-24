package com.b4g.sid.books4geeks.Model;

/**
 * Created by Sid on 24-Dec-16.
 */

public class BestSeller {

    private String title;
    private String author;
    private String desc;
    private String isbn10;
    private String isbn13;
    private String urlImage;


    public BestSeller(String title, String author, String desc, String isbn10, String isbn13, String urlImage) {
        this.title = title;
        this.author = author;
        this.desc = desc;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.urlImage = urlImage;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDesc() {
        return desc;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public String getUrlImage() {
        return urlImage;
    }
}
