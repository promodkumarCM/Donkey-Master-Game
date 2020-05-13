package com.sparklingapps.cardgamefrog.utils;

import android.content.Context;
import android.graphics.Typeface;

import com.sparklingapps.cardgamefrog.R;


/**
 * Created by Basil1112
 */
public class Font {
    public final static int LATO_REGULAR = 0;
    public final static int LATO_LIGHT = 1;
    public final static int LATO_LIGHT_ITALIC = 2;
    public final static int LATO_HAIRLINE = 3;
    public final static int ROBOTO_REGULAR = 4;

    public static Typeface getFontType(Context context, int fontSetInXML) {
        int fontName = 0;
        switch (fontSetInXML) {
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

        String customFont = context.getResources().getString(fontName);


        if (customFont == null) {
            return null;
        } else
            return Typeface.createFromAsset(context.getAssets(), "font/" + customFont);

    }
}