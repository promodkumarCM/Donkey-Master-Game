package com.sparklingapps.cardgamefrog.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;


public class CardImageView extends AppCompatImageView {

    public CardImageView(Context context) {
        super(context);
    }

    public CardImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CardImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
    }

    public void select() {
        if (isSelected()) {
            //Already selected do nothing
        } else {
            //Adding margin
            ViewUtil.setMargin(this, 0, 0, 0, 16,ViewUtil.LINEARLAYOUT);
            setSelected(true);
        }
    }

    public void unSelect() {
        if (isSelected()) {
            //Removing margin
            ViewUtil.setMargin(this, 0, 0, 0, 0,ViewUtil.LINEARLAYOUT);
            setSelected(false);
        } else {
            //Not selected do nothing
        }
    }

}