package com.b4g.sid.books4geeks.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.ui.Fragment.SearchFragment;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String searchQuery = getIntent().getStringExtra(B4GAppClass.SEARCH_QUERY);
        if(searchQuery!=null){
            SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_fragment);
            searchFragment.performSearchFor(searchQuery);
        }
    }

}
