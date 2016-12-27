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
    private String publisher;
    private String imageUrl;
    private String publisherDate;
    private String rating;
    private String pageCount;
    private String voteCount;
    private String volumeId;

    public BookDetail(String title, String authors, String desc, String publisher, String imageUrl,
                      String publisherDate,String rating, String pageCount, String voteCount, String volumeId) {
        this.title = title;
        this.authors = authors;
        this.desc = desc;
        this.publisher = publisher;
        this.imageUrl = imageUrl;
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
        this.publisher = source.readString();
        this.imageUrl = source.readString();
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

    public String getPublisher() {
        return publisher;
    }

    public String getImageUrl() {
        return imageUrl;
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
        dest.writeString(publisher);
        dest.writeString(imageUrl);
        dest.writeString(publisherDate);
        dest.writeString(rating);
        dest.writeString(pageCount);
        dest.writeString(voteCount);
        dest.writeString(volumeId);
    }
}
