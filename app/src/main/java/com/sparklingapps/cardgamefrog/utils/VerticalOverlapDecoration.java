package com.sparklingapps.cardgamefrog.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class VerticalOverlapDecoration extends RecyclerView.ItemDecoration {

    private final static int overlap = -180;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        final int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == 0) {
            outRect.set(0, 0, 0, overlap);
        }
        else if (itemPosition == (parent.getAdapter().getItemCount() - 1)) {
            outRect.set(0, 0, 0, 0);
        }
        else {
            outRect.set(0, 0, 0, overlap);
        }
    }
}