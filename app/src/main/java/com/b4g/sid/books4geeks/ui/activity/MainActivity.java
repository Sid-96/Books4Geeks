package com.b4g.sid.books4geeks.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.Model.BookDetail;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.DimensionUtil;
import com.b4g.sid.books4geeks.ui.Fragment.BestSellerFragment;
import com.b4g.sid.books4geeks.ui.Fragment.DetailFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null && DimensionUtil.isTablet()){
            loadDetailFragmentforTablet(null,false);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onBackPressed() {

        BestSellerFragment fragment = (BestSellerFragment) getSupportFragmentManager().findFragmentByTag(B4GAppClass.TAG_BESTSELLER_FRAGMENT);
        if(fragment!=null && fragment.back){
            fragment.navigateToCategories();
            if(DimensionUtil.isTablet()){
                loadDetailFragmentforTablet(null,true);
            }
        }
        else{
            super.onBackPressed();
        }
    }

    public void loadDetailFragmentforTablet(BookDetail bookDetail, boolean isMsgVisible){
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(B4GAppClass.BOOK_DETAIL,bookDetail);
        bundle.putBoolean(B4GAppClass.MSG_VISIBILITY,isMsgVisible);
        detailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment,detailFragment).commit();
    }
}
