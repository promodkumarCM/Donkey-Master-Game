package com.sparklingapps.cardgamefrog.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputEditText;
import com.sparklingapps.cardgamefrog.R;


/**
 * Created by Basil1112
 */

public class CardGameEditText extends AppCompatEditText {

    public CardGameEditText(Context context) {
        super(context);
    }

    public CardGameEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context, attrs);
    }

    private void setFont(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.CardGameEditText);
            int font = array.getInt(R.styleable.CardGameEditText_fontName, -1);
            Typeface typeface = Font.getFontType(context, font);
            if (typeface != null) {
                setTypeface(typeface);
            }
            array.recycle();
        }
    }
}