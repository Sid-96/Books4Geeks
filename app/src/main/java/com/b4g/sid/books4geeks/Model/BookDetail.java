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
    private String infoLink;
    private String publisherDate;
    private String rating;
    private String pageCount;
    private String voteCount;
    private String uniqueId;

    public BookDetail(String title, String authors, String desc, String publisher, String imageUrl,
                      String infoLink, String publisherDate, String rating, String pageCount, String voteCount, String uniqueId) {
        this.title = title;
        this.authors = authors;
        this.desc = desc;
        this.publisher = publisher;
        this.imageUrl = imageUrl;
        this.infoLink = infoLink;
        this.publisherDate = publisherDate;
        this.rating = rating;
        this.pageCount = pageCount;
        this.voteCount = voteCount;
        this.uniqueId = uniqueId;
    }

    public BookDetail(Parcel source) {
        this.title = source.readString();
        this.authors = source.readString();
        this.desc = source.readString();
        this.publisher = source.readString();
        this.imageUrl = source.readString();
        this.infoLink = source.readString();
        this.publisherDate = source.readString();
        this.rating = source.readString();
        this.pageCount = source.readString();
        this.voteCount = source.readString();
        this.uniqueId = source.readString();

    }

    public BookDetail(BestSeller bestSeller){
        this.title = bestSeller.getTitle();
        this.authors = bestSeller.getAuthor();
        this.desc = bestSeller.getDesc();
        this.publisher = "";
        this.imageUrl = bestSeller.getUrlImage();
        this.infoLink = bestSeller.getItemUrl();
        this.publisherDate = "";
        this.rating = "";
        this.pageCount = "";
        this.voteCount = "";
        this.uniqueId = "";
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

    public String getInfoLink() {
        return infoLink;
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

    public String getUniqueId() {
        return uniqueId;
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
        dest.writeString(infoLink);
        dest.writeString(publisherDate);
        dest.writeString(rating);
        dest.writeString(pageCount);
        dest.writeString(voteCount);
        dest.writeString(uniqueId);
    }
}
