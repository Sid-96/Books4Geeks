package com.b4g.sid.books4geeks.ui.Fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.b4g.sid.books4geeks.B4GAppClass;
import com.b4g.sid.books4geeks.CustomViews.ItemDecorationView;
import com.b4g.sid.books4geeks.Model.BookDetail;
import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.DBUtil;
import com.b4g.sid.books4geeks.Util.DimensionUtil;
import com.b4g.sid.books4geeks.adapter.BookCursorAdapter;
import com.b4g.sid.books4geeks.data.BookColumns;
import com.b4g.sid.books4geeks.data.BookProvider;
import com.b4g.sid.books4geeks.ui.activity.DetailBookActivity;
import com.b4g.sid.books4geeks.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogFragment extends Fragment implements BookCursorAdapter.OnBookClickListener,LoaderManager.LoaderCallbacks<Cursor>{

    @BindView(R.id.catalog_list)        RecyclerView catalogList;
    @BindView(R.id.progress_circle)     View progressCircle;
    @BindView(R.id.catalog_holder)      View catalogHolder;

    private View view;
    private Unbinder unbinder;
    private int shelf;
    private BookCursorAdapter cursorAdapter;
    private static final int CURSOR_LOADER_ID = 9;
    public CatalogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_catalog,container,false);
        setRetainInstance(true);
        unbinder = ButterKnife.bind(this,view);
        shelf = getArguments().getInt(B4GAppClass.BOOK_SHELF);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), DimensionUtil.getNumberOfColumns(R.dimen.book_card_width,1));
        cursorAdapter = new BookCursorAdapter(this);
        catalogList.setHasFixedSize(true);
        catalogList.setLayoutManager(layoutManager);
        catalogList.addItemDecoration(new ItemDecorationView(getContext(),R.dimen.recycler_item_padding));
        catalogList.setAdapter(cursorAdapter);
        loadBooks(B4GAppClass.SORT_TITLE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onBookClicked(int position) {
        BookDetail bookDetail = cursorAdapter.getItem(position);
        if(DimensionUtil.isTablet())
            ((MainActivity)getActivity()).loadDetailFragmentforTablet(bookDetail,false);
        else{
            Intent intent = new Intent(getContext(), DetailBookActivity.class);
            intent.putExtra(B4GAppClass.BOOK_DETAIL,bookDetail);
            startActivity(intent);
        }

    }

    @Override
    public void onBookMenuClicked(int position, View view) {
        BookDetail bookDetail = cursorAdapter.getItem(position);
        int shelf = DBUtil.getCurrentShelf(bookDetail);
        DBUtil.getPopupMenu(getActivity(),bookDetail,shelf,view).show();
    }

    public void loadBooks(int sortOrder){
        Bundle bundle = new Bundle();
        if(sortOrder==B4GAppClass.SORT_TITLE)   bundle.putString(B4GAppClass.SORT,BookColumns.TITLE + " ASC");
        else if(sortOrder == B4GAppClass.SORT_AUTHOR)   bundle.putString(B4GAppClass.SORT,BookColumns.AUTHORS + " ASC");
        getLoaderManager().initLoader(CURSOR_LOADER_ID,bundle,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id == CURSOR_LOADER_ID){
            return new CursorLoader(getContext(), BookProvider.Books.CONTENT_URI,new String[]{ },
                    BookColumns.SHELF + " = '" + shelf + "'",null,args.getString(B4GAppClass.SORT));
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data!=null && data.getCount()>0){
            catalogHolder.setVisibility(View.GONE);
            progressCircle.setVisibility(View.GONE);
            catalogList.setVisibility(View.VISIBLE);
            cursorAdapter.swapCursor(data);
        }
        else{
            catalogList.setVisibility(View.GONE);
            progressCircle.setVisibility(View.GONE);
            catalogHolder.setVisibility(View.VISIBLE);
        }

        if(DimensionUtil.isTablet()){
            view.post(new Runnable() {
                @Override
                public void run() {
                    ((MainActivity)getActivity()).loadDetailFragmentforTablet(cursorAdapter.getItem(0),false);
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
