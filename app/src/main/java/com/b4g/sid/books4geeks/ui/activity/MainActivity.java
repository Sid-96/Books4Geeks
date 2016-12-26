package com.b4g.sid.books4geeks.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.ui.Fragment.BestSellerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {

        BestSellerFragment fragment = (BestSellerFragment) getSupportFragmentManager().findFragmentByTag(B4GAppClass.TAG_BESTSELLER_FRAGMENT);
        if(fragment!=null && fragment.back){
            fragment.navigateToCategories();
        }
        else{
            super.onBackPressed();
        }
    }
}
