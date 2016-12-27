package com.b4g.sid.books4geeks.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sid on 27-Dec-16.
 */

public class BookDetail implements Parcelable{

    private String title;
    private String authors;
    private String desc;
    private String isbn10;
    private String isbn13;
    private String publisher;
    private String publisherDate;
    private String rating;
    private String pageCount;
    private String voteCount;
    private String volumeId;

    public BookDetail(String title, String authors, String desc, String isbn10, String isbn13,
                      String publisher, String publisherDate, String rating, String pageCount,
                      String voteCount, String volumeId) {
        this.title = title;
        this.authors = authors;
        this.desc = desc;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.publisher = publisher;
        this.publisherDate = publisherDate;
        this.rating = rating;
        this.pageCount = pageCount;
        this.voteCount = voteCount;
        this.volumeId = volumeId;
    }

    public BookDetail(Parcel source) {
        this.title = source.readString();
        this.authors = source.readString();
        this.desc = source.readString();
        this.isbn10 = source.readString();
        this.isbn13 = source.readString();
        this.publisher = source.readString();
        this.publisherDate = source.readString();
        this.rating = source.readString();
        this.pageCount = source.readString();
        this.voteCount = source.readString();
        this.volumeId = source.readString();

    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
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

    public String getPublisher() {
        return publisher;
    }

    public String getPublisherDate() {
        return publisherDate;
    }

    public String getRating() {
        return rating;
    }

    public String getPageCount() {
        return pageCount;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public BookDetail createFromParcel(Parcel source) {
            return new BookDetail(source);
        }

        @Override
        public BookDetail[] newArray(int size) {
            return new BookDetail[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(authors);
        dest.writeString(desc);
        dest.writeString(isbn10);
        dest.writeString(isbn13);
        dest.writeString(publisher);
        dest.writeString(publisherDate);
        dest.writeString(rating);
        dest.writeString(pageCount);
        dest.writeString(voteCount);
        dest.writeString(volumeId);
    }
}
