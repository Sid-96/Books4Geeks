package com.b4g.sid.books4geeks.ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.VolleySingleton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.toolbar)             Toolbar toolbar;
    @BindView(R.id.search_toolbar)      EditText searchBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VolleySingleton.getInstance().requestQueue.cancelAll(getClass().getName());
        unbinder.unbind();
    }
}
