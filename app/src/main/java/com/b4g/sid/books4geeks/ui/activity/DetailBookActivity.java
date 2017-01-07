package com.b4g.sid.books4geeks.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.ui.Fragment.DetailFragment;

public class DetailBookActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);
        if(savedInstanceState==null){
            DetailFragment detailFragment = new DetailFragment();

            Bundle args = new Bundle();
            args.putParcelable(B4GAppClass.BOOK_DETAIL,getIntent().getParcelableExtra(B4GAppClass.BOOK_DETAIL));
            detailFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.activity_detail_book,detailFragment).commit();
        }
    }
}
