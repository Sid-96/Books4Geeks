package com.b4g.sid.books4geeks.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.Model.BestSeller;
import com.b4g.sid.books4geeks.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sid on 24-Dec-16.
 */

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.MyViewHolder> {

    private OnBestSellerClickListener onBestSellerClickListener;
    private ArrayList<BestSeller> bestSellersList;
    private Context context;

    public BestSellerAdapter(OnBestSellerClickListener onBestSellerClickListener){
        this.context = B4GAppClass.getAppContext();
        this.onBestSellerClickListener = onBestSellerClickListener;
        this.bestSellersList = new ArrayList<>();
    }

    public BestSellerAdapter(ArrayList<BestSeller> bestSellersList, OnBestSellerClickListener onBestSellerClickListener){
        this.bestSellersList = bestSellersList;
        this.onBestSellerClickListener = onBestSellerClickListener;
    }

    public void addToList(BestSeller bestSeller){
        bestSellersList.add(bestSeller);
    }

    public ArrayList<BestSeller> getBestSellersList(){return bestSellersList;}

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bestseller,parent,false);
        return new MyViewHolder(v,onBestSellerClickListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        BestSeller bestSeller = bestSellersList.get(position);
        holder.bookTitle.setText(bestSeller.getTitle());
        holder.bookAuthor.setText(bestSeller.getAuthor());
        if(bestSeller.getUrlImage()!=null && bestSeller.getUrlImage().length()>0){
            Picasso.with(context).load(bestSeller.getUrlImage()).into(holder.bookImage);
        }
        else {
            holder.bookImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.category_temp));
        }
    }

    @Override
    public int getItemCount() {
        return bestSellersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.book_card)       CardView cardView;
        @BindView(R.id.book_title)      TextView bookTitle;
        @BindView(R.id.book_author)     TextView bookAuthor;
        @BindView(R.id.book_image)      ImageView bookImage;
        @BindView(R.id.book_options)    View bookOptions;

        public MyViewHolder(View itemView, final OnBestSellerClickListener onBestSellerClickListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBestSellerClickListener.onBestSellerClicked(getAdapterPosition());
                }
            });
            bookOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBestSellerClickListener.onBestSellerMenuClicked(getAdapterPosition(),bookOptions);
                }
            });
        }
    }

    public interface OnBestSellerClickListener{
        void onBestSellerClicked(final int position);
        void onBestSellerMenuClicked(final int position, View view);
    }
}
