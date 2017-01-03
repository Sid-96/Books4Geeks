package com.b4g.sid.books4geeks.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.b4g.sid.books4geeks.Model.Category;
import com.b4g.sid.books4geeks.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sid on 23-Dec-16.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    OnCategoryItemClickListener onCategoryItemClickListener;

    public CategoryAdapter(OnCategoryItemClickListener onCategoryItemClickListener){
        this.onCategoryItemClickListener = onCategoryItemClickListener;
    }


    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category,parent,false);
        return new CategoryViewHolder(v,onCategoryItemClickListener);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.CategoryViewHolder holder, int position) {
        holder.categoryName.setText(Category.getCategoryList().get(position).getDisplayName());
        holder.categoryIcon.setImageResource(R.drawable.category_temp);

    }

    @Override
    public int getItemCount() {
        return Category.getCategoryList().size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category_item)   View categoryItem;
        @BindView(R.id.category_icon)   ImageView categoryIcon;
        @BindView(R.id.category_name)   TextView categoryName;

        public CategoryViewHolder(View itemView, final OnCategoryItemClickListener onCategoryItemClickListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            categoryItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCategoryItemClickListener.onCategoryItemClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface OnCategoryItemClickListener{
        void onCategoryItemClicked(final int position);
    }

}
