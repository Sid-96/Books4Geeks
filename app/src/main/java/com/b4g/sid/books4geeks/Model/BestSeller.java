package com.b4g.sid.books4geeks.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sid on 24-Dec-16.
 */

public class BestSeller implements Parcelable{

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

    public BestSeller(Parcel source) {
        this.title = source.readString();
        this.author = source.readString();
        this.desc = source.readString();
        this.isbn10 = source.readString();
        this.isbn13 = source.readString();
        this.urlImage = source.readString();

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

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public BestSeller createFromParcel(Parcel source) {
            return new BestSeller(source);
        }

        @Override
        public BestSeller[] newArray(int size) {
            return new BestSeller[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(desc);
        dest.writeString(isbn10);
        dest.writeString(isbn13);
        dest.writeString(urlImage);
    }
}
