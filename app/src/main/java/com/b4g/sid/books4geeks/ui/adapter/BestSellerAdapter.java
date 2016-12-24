package com.b4g.sid.books4geeks.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.b4g.sid.books4geeks.Model.BestSeller;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.VolleySingleton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sid on 24-Dec-16.
 */

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.MyViewHolder> {

    private OnBestSellerClickListener onBestSellerClickListener;
    private ArrayList<BestSeller> bestSellersList;

    public BestSellerAdapter(OnBestSellerClickListener onBestSellerClickListener){
        this.onBestSellerClickListener = onBestSellerClickListener;
        this.bestSellersList = new ArrayList<>();
    }

    public void addToList(BestSeller bestSeller){
        bestSellersList.add(bestSeller);
    }

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
        VolleySingleton.getInstance().imageLoader.get(bestSeller.getUrlImage(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.bookImage.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                // load default
            }
        });
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

        public MyViewHolder(View itemView, final OnBestSellerClickListener onBestSellerClickListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBestSellerClickListener.onBestSellerClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface OnBestSellerClickListener{
        void onBestSellerClicked(final int position);
    }
}
