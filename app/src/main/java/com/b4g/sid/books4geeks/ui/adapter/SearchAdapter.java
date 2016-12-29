package com.b4g.sid.books4geeks.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.Model.BestSeller;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.VolleySingleton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sid on 29-Dec-16.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


    private OnSearchBookClickListener onSearchBookClickListener;
    private ArrayList<BestSeller> bestSellersList;

    public SearchAdapter(OnSearchBookClickListener onSearchBookClickListener){
        this.onSearchBookClickListener = onSearchBookClickListener;
        this.bestSellersList = new ArrayList<>();
    }

    public SearchAdapter(ArrayList<BestSeller> bestSellersList, OnSearchBookClickListener onSearchBookClickListener){
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
        BestSeller bestSeller = bestSellersList.get(position);
        if(bestSeller.getUrlImage().length()==0){
            holder.bookImage.setImageDrawable(ContextCompat.getDrawable(B4GAppClass.getAppContext(),R.drawable.category_temp));
        }
        else {
            holder.bookImage.setImageUrl(bestSeller.getUrlImage(), VolleySingleton.getInstance().imageLoader);
        }
        holder.bookTitle.setText(bestSeller.getTitle());
        if(bestSeller.getAuthor().length()==0){
            holder.author.setVisibility(View.GONE);
        }
        else{
            holder.author.setText(bestSeller.getAuthor());
        }
    }

    @Override
    public int getItemCount() {
        return bestSellersList.size();
    }

    public ArrayList<BestSeller> getBestSellersList(){
        return  bestSellersList;
    }

    public void addToList(BestSeller bestSeller){
        bestSellersList.add(bestSeller);
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.book_card)   CardView bookCard;
        @BindView(R.id.book_image)  NetworkImageView bookImage;
        @BindView(R.id.book_title)  TextView bookTitle;
        @BindView(R.id.book_author) TextView author;


        public SearchViewHolder(View itemView, final OnSearchBookClickListener onSearchBookClickListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            bookCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSearchBookClickListener.onBookClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface OnSearchBookClickListener{
        void onBookClicked(int position);
    }
}
