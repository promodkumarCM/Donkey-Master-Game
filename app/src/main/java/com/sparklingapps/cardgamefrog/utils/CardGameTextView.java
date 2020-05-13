package com.sparklingapps.cardgamefrog.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;

import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.sparklingapps.cardgamefrog.R;


/**
 * Created by basil on 08/16/18
 */

public class CardGameTextView extends AppCompatTextView {

    String customFont;

    public CardGameTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        style(context, attrs);
    }


    public CardGameTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        style(context, attrs);
    }

    private void style(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CardGameTextView);
        int cf = a.getInteger(R.styleable.CardGameTextView_textfontName, 0);
        int fontName = 0;
        switch (cf) {
            case 1:
                fontName = R.string.muli_regular;
                break;
            case 2:
                fontName = R.string.TheBomb;
                break;
            case 3:
                fontName = R.string.KingOfPirateBold;
                break;
            default:
                fontName = R.string.muli_regular;
                break;
        }

        customFont = getResources().getString(fontName);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "font/" + customFont);

        setTypeface(tf);
        a.recycle();


    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

    }

}