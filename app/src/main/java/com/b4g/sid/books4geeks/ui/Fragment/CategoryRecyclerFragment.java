package com.b4g.sid.books4geeks.ui.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.ui.adapter.CategoryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryRecyclerFragment extends Fragment implements CategoryAdapter.OnCategoryItemClickListener {


    Unbinder unbinder;
    @BindView(R.id.category_list)  RecyclerView categoryList;
    public CategoryRecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category_recycler, container, false);
        unbinder = ButterKnife.bind(this,v);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        CategoryAdapter categoryAdapter = new CategoryAdapter(this);
        categoryList.setHasFixedSize(true);
        categoryList.setLayoutManager(linearLayoutManager);
        categoryList.setAdapter(categoryAdapter);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onCategoryItemClicked(int position) {

    }
}
