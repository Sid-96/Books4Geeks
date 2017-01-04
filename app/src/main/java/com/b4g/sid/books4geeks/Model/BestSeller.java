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
    private String currentRank;
    private String weeksOnList;
    private String isbn10;
    private String isbn13;
    private String urlImage;
    private String itemUrl;



    public BestSeller(String title, String author, String desc, String currentRank,
                      String weeksOnList, String isbn10, String isbn13, String urlImage, String itemUrl) {
        this.title = title;
        this.author = author;
        this.desc = desc;
        this.currentRank = currentRank;
        this.weeksOnList = weeksOnList;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.urlImage = urlImage;
        this.itemUrl = itemUrl;
    }

    public BestSeller(Parcel source) {
        this.title = source.readString();
        this.author = source.readString();
        this.desc = source.readString();
        this.currentRank = source.readString();
        this.weeksOnList = source.readString();
        this.isbn10 = source.readString();
        this.isbn13 = source.readString();
        this.urlImage = source.readString();
        this.itemUrl = source.readString();
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

    public String getItemUrl() {
        return itemUrl;
    }

    public String getCurrentRank() {
        return currentRank;
    }

    public String getWeeksOnList() {
        return weeksOnList;
    }

    public String getUniqueId() {
        if (isbn10!=null && isbn10.length()>0) {
            return "nyt1_" + isbn10;
        }
        else if (isbn13!=null && isbn13.length() > 0) {
            return "nyt2_" + isbn13;
        }
        else {
            return "nyt3_" + title.toLowerCase().replaceAll("[^A-Za-z0-9]","") + author.toLowerCase().replaceAll("[^A-Za-z0-9]","");
        }
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
        dest.writeString(currentRank);
        dest.writeString(weeksOnList);
        dest.writeString(isbn10);
        dest.writeString(isbn13);
        dest.writeString(urlImage);
        dest.writeString(itemUrl);
    }
}
