package com.b4g.sid.books4geeks.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sid on 27-Dec-16.
 */

public class BookDetail implements Parcelable{

    private String title;
    private String subtitle;
    private String authors;
    private String desc;
    private String publisher;
    private String isbn10;
    private String isbn13;
    private String imageUrl;
    private String infoLink;
    private String currentRank;
    private String weeksOnList;
    private String publisherDate;
    private String rating;
    private String pageCount;
    private String voteCount;
    private String uniqueId;

    public BookDetail(String title, String subtitle, String authors, String desc, String publisher, String isbn10, String isbn13, String imageUrl,
                      String infoLink, String publisherDate, String pageCount, String voteCount, String uniqueId, String rating) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.desc = desc;
        this.publisher = publisher;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.imageUrl = imageUrl;
        this.infoLink = infoLink;
        this.currentRank = "";
        this.weeksOnList = "";
        this.publisherDate = publisherDate;
        this.rating = rating;
        this.pageCount = pageCount;
        this.voteCount = voteCount;
        this.uniqueId = uniqueId;
    }

    public BookDetail(Parcel source) {
        this.title = source.readString();
        this.subtitle = source.readString();
        this.authors = source.readString();
        this.desc = source.readString();
        this.publisher = source.readString();
        this.isbn10 = source.readString();
        this.isbn13 = source.readString();
        this.imageUrl = source.readString();
        this.infoLink = source.readString();
        this.currentRank = source.readString();
        this.weeksOnList = source.readString();
        this.publisherDate = source.readString();
        this.rating = source.readString();
        this.pageCount = source.readString();
        this.voteCount = source.readString();
        this.uniqueId = source.readString();

    }

    public BookDetail(BestSeller bestSeller){
        this.title = bestSeller.getTitle();
        this.subtitle = "";
        this.authors = bestSeller.getAuthor();
        this.desc = bestSeller.getDesc();
        this.publisher = "";
        this.isbn10 = bestSeller.getIsbn10();
        this.isbn13 = bestSeller.getIsbn13();
        this.imageUrl = bestSeller.getUrlImage();
        this.infoLink = bestSeller.getItemUrl();
        this.currentRank = bestSeller.getCurrentRank();
        this.weeksOnList = bestSeller.getWeeksOnList();
        this.publisherDate = "";
        this.rating = "";
        this.pageCount = "";
        this.voteCount = "";
        this.uniqueId = "";
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
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

    public String getUniqueIdentifier() {
        if((isbn10==null || isbn10.length()==0) && (isbn13==null || isbn13.length()==0)){
            return "";
        }
        else if (isbn10!=null && isbn10.length() > 0) {
            return  isbn13;
        }
        else if (isbn13!=null && isbn13.length()>0) {
            return  isbn10;
        }
        else {
            return  isbn10+", "+isbn13;
        }
    }

    public String getIsbn10() {
        return isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public String getCurrentRank() {
        return currentRank;
    }

    public String getWeeksOnList() {
        return weeksOnList;
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
        dest.writeString(subtitle);
        dest.writeString(authors);
        dest.writeString(desc);
        dest.writeString(publisher);
        dest.writeString(isbn10);
        dest.writeString(isbn13);
        dest.writeString(imageUrl);
        dest.writeString(infoLink);
        dest.writeString(currentRank);
        dest.writeString(weeksOnList);
        dest.writeString(publisherDate);
        dest.writeString(rating);
        dest.writeString(pageCount);
        dest.writeString(voteCount);
        dest.writeString(uniqueId);
    }
}
