package com.b4g.sid.books4geeks.CustomViews;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Sid on 25-Dec-16.
 */

public class ItemDecorationView extends RecyclerView.ItemDecoration {

    private int itemOffset;

    public ItemDecorationView(int itemOffset){
        this.itemOffset = itemOffset;
    }

    public ItemDecorationView(@NonNull Context context, @DimenRes int itemOffsetId){
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(itemOffset,itemOffset,itemOffset,itemOffset);
    }
}
