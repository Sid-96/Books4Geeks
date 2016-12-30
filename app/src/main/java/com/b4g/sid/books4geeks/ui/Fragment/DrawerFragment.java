package com.b4g.sid.books4geeks.ui.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.DimensionUtil;
import com.b4g.sid.books4geeks.ui.activity.BarCodeScannerActivity;
import com.b4g.sid.books4geeks.ui.activity.MainActivity;
import com.b4g.sid.books4geeks.ui.activity.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    Unbinder unbinder;
    @BindView(R.id.toolbar)         Toolbar toolbar;
    @BindView(R.id.nav_view)        NavigationView navigationView;
    @BindView(R.id.drawer_layout)   DrawerLayout drawerLayout;

    public DrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        unbinder = ButterKnife.bind(this,view);
        toolbar.setTitle(R.string.app_name);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,
                toolbar,R.string.app_name,R.string.app_name){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle.syncState();
        if(savedInstanceState!=null && savedInstanceState.containsKey(B4GAppClass.TOOLBAR_TITLE)){
            toolbar.setTitle(savedInstanceState.getString(B4GAppClass.TOOLBAR_TITLE));
        }
        return  view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(B4GAppClass.TOOLBAR_TITLE,toolbar.getTitle().toString());
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawers();
        toolbar.setTitle(item.getTitle());
        item.setChecked(true);
        if(item.getItemId()==R.id.item_bestsellers){
            BestSellerFragment fragment = new BestSellerFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_fragment,fragment,B4GAppClass.TAG_BESTSELLER_FRAGMENT);
            transaction.commit();
            if(DimensionUtil.isTablet()){
                ((MainActivity)getActivity()).loadDetailFragmentforTablet(null,true);
            }
            return false;
        }
        else if(item.getItemId()==R.id.item_search){
            startActivity(new Intent(getContext(), SearchActivity.class));
            return false;
        }

        else if(item.getItemId()==R.id.item_barcode){
            startActivity(new Intent(getContext(), BarCodeScannerActivity.class));
            return false;
        }
        return true;
    }
}
