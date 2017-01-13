package com.b4g.sid.books4geeks.ui.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.PreferenceUtil;
import com.b4g.sid.books4geeks.data.BookColumns;
import com.b4g.sid.books4geeks.ui.activity.BarCodeScannerActivity;
import com.b4g.sid.books4geeks.ui.activity.SearchActivity;
import com.b4g.sid.books4geeks.ui.activity.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, Toolbar.OnMenuItemClickListener {

    Unbinder unbinder;
    View view;
    private static final int CAMERA_REQUEST_CODE = 9;
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
        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        unbinder = ButterKnife.bind(this,view);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.menu_drawer_extra);
        toolbar.setOnMenuItemClickListener(this);
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
        if(savedInstanceState==null){
            toolbar.setTitle(R.string.drawer_bestsellers);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,new BestSellerFragment(),B4GAppClass.TAG_BESTSELLER_FRAGMENT).commit();

        }
        else if(savedInstanceState!=null && savedInstanceState.containsKey(B4GAppClass.TOOLBAR_TITLE)){
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startActivity(new Intent(getContext(),BarCodeScannerActivity.class));
                }
                else {
                    Toast.makeText(getContext(), R.string.camera_permission_denied,Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawers();
        if(item.getItemId()==R.id.item_bestsellers){
            toolbar.setTitle(R.string.drawer_bestsellers);
            toolbar.getMenu().findItem(R.id.item_sort).setVisible(false);
            BestSellerFragment fragment = new BestSellerFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_fragment,fragment,B4GAppClass.TAG_BESTSELLER_FRAGMENT);
            transaction.commit();
            return true;
        }
        else if(item.getItemId()==R.id.item_search){
            startActivity(new Intent(getContext(), SearchActivity.class));
        }

        else if(item.getItemId()==R.id.item_barcode){
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
            }
            else{
                startActivity(new Intent(getContext(), BarCodeScannerActivity.class));
            }
        }
        else if(item.getItemId()==R.id.item_to_read){
            toolbar.setTitle(R.string.drawer_to_read);
            toolbar.getMenu().findItem(R.id.item_sort).setVisible(true);
            CatalogFragment toReadFragment = new CatalogFragment();
            Bundle args = new Bundle();
            args.putInt(B4GAppClass.BOOK_SHELF, BookColumns.SHELF_TO_READ);
            toReadFragment.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,toReadFragment,B4GAppClass.TAG_CATALOG_FRAGMENT).commit();
            return true;
        }
        else if(item.getItemId()==R.id.item_reading){
            toolbar.setTitle(R.string.drawer_reading);
            toolbar.getMenu().findItem(R.id.item_sort).setVisible(true);
            CatalogFragment readingFragment = new CatalogFragment();
            Bundle args = new Bundle();
            args.putInt(B4GAppClass.BOOK_SHELF, BookColumns.SHELF_READING);
            readingFragment.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,readingFragment,B4GAppClass.TAG_CATALOG_FRAGMENT).commit();
            return true;
        }
        else if(item.getItemId()==R.id.item_completed){
            toolbar.setTitle(R.string.drawer_completed);
            toolbar.getMenu().findItem(R.id.item_sort).setVisible(true);
            CatalogFragment completedFragment = new CatalogFragment();
            Bundle args = new Bundle();
            args.putInt(B4GAppClass.BOOK_SHELF, BookColumns.SHELF_FINISHED);
            completedFragment.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,completedFragment,B4GAppClass.TAG_CATALOG_FRAGMENT).commit();
            return true;
        }

        else if(item.getItemId()==R.id.item_about){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(getActivity().getLayoutInflater().inflate(R.layout.layout_about,null));
            builder.show();
        }
        else if(item.getItemId()==R.id.item_settings){
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId() == R.id.item_email){
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","sidsinh2011@gmail.com",null));
            intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.app_name));
            try {
                startActivity(Intent.createChooser(intent,getString(R.string.email)));
            }
            catch (Exception e){
                Toast.makeText(getContext(),getString(R.string.email_error),Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        else if(item.getItemId() == R.id.item_sort){
            PopupMenu popupMenu = new PopupMenu(getContext(),view.findViewById(R.id.item_sort));
            popupMenu.inflate(R.menu.menu_sort);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if(item.getItemId()==R.id.sort_title){
                        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(B4GAppClass.TAG_CATALOG_FRAGMENT);
                        PreferenceUtil.setSortType(B4GAppClass.SORT_TITLE);
                        if(fragment!=null){
                            ((CatalogFragment)fragment).loadBooks();
                        }
                        return true;
                    }
                    else if(item.getItemId()==R.id.sort_author){
                        PreferenceUtil.setSortType(B4GAppClass.SORT_AUTHOR);
                        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(B4GAppClass.TAG_CATALOG_FRAGMENT);
                        if(fragment!=null){
                            ((CatalogFragment)fragment).loadBooks();
                        }
                        return  true;
                    }
                    return false;
                }
            });
            popupMenu.show();
            return true;
        }
        return false;
    }
}
