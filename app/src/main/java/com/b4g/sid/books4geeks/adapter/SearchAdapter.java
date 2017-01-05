package com.b4g.sid.books4geeks.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.Model.BookDetail;
import com.b4g.sid.books4geeks.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sid on 29-Dec-16.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


    private OnSearchBookClickListener onSearchBookClickListener;
    private ArrayList<BookDetail> bestSellersList;

    public SearchAdapter(OnSearchBookClickListener onSearchBookClickListener){
        this.onSearchBookClickListener = onSearchBookClickListener;
        this.bestSellersList = new ArrayList<>();
    }

    public SearchAdapter(ArrayList<BookDetail> bestSellersList, OnSearchBookClickListener onSearchBookClickListener){
        this.onSearchBookClickListener = onSearchBookClickListener;
        this.bestSellersList = bestSellersList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bestseller,parent,false);
        return new SearchViewHolder(v,onSearchBookClickListener);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        BookDetail bestSeller = bestSellersList.get(position);
        if(bestSeller.getImageUrl()!=null && bestSeller.getImageUrl().length()>0){
            Picasso.with(B4GAppClass.getAppContext()).load(bestSeller.getImageUrl()).fit().centerCrop().into(holder.bookImage);
        }
        else {
            holder.bookImage.setImageDrawable(ContextCompat.getDrawable(B4GAppClass.getAppContext(),R.drawable.category_temp));
        }
        holder.bookTitle.setText(bestSeller.getTitle());
        if(bestSeller.getAuthors().length()==0){
            holder.author.setVisibility(View.GONE);
        }
        else{
            holder.author.setText(bestSeller.getAuthors());
        }
    }

    @Override
    public int getItemCount() {
        return bestSellersList.size();
    }

    public ArrayList<BookDetail> getBestSellersList(){
        return  bestSellersList;
    }

    public void addToList(BookDetail bestSeller){
        bestSellersList.add(bestSeller);
    }

    public void setBestSellersList(ArrayList<BookDetail> bestSellersList) {
        this.bestSellersList = bestSellersList;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.book_card)   CardView bookCard;
        @BindView(R.id.book_image)  ImageView bookImage;
        @BindView(R.id.book_title)  TextView bookTitle;
        @BindView(R.id.book_author) TextView author;
        @BindView(R.id.book_options) View bookOptions;


        public SearchViewHolder(final View itemView, final OnSearchBookClickListener onSearchBookClickListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            bookCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSearchBookClickListener.onBookClicked(getAdapterPosition());
                }
            });
            bookOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSearchBookClickListener.onBookMenuClicked(getAdapterPosition(),bookOptions);
                }
            });
        }
    }

    public interface OnSearchBookClickListener{
        void onBookClicked(final int position);
        void onBookMenuClicked(final int position,View view);
    }
}
