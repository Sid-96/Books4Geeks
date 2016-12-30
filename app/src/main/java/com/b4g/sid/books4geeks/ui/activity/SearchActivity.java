package com.b4g.sid.books4geeks.ui.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.Model.BookDetail;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.DimensionUtil;
import com.b4g.sid.books4geeks.ui.Fragment.DetailFragment;
import com.b4g.sid.books4geeks.ui.Fragment.SearchFragment;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(savedInstanceState==null && DimensionUtil.isTablet()){
            loadDetailFragmentforTablet(null);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        String searchQuery = getIntent().getStringExtra(B4GAppClass.SEARCH_QUERY);
        if(searchQuery!=null){
            SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_fragment);
            searchFragment.performSearchFor(searchQuery);
        }
    }

    public void loadDetailFragmentforTablet(BookDetail bookDetail){
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(B4GAppClass.BOOK_DETAIL,bookDetail);
        detailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment,detailFragment).commit();
    }


}
