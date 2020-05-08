package com.sparklingapps.cardgamefrog.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class CardImageView extends androidx.appcompat.widget.AppCompatImageView {

    private boolean isSelected;

    public CardImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }


}
