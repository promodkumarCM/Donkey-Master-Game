package com.sparklingapps.cardgamefrog.utils;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewUtil {

    public static final int RELATIVELAYOUT = 100;
    public static final int LINEARLAYOUT = 200;

    public static final int WEIGHT = 640;
    public static final int HEIGHT = 1136;

    public static final int HEIGHT_1280 = 1280;
    public static final int WIDTH_720 = 720;

    public static final int HEIGHT_1920 = 1920;
    public static final int WIDTH_1080 = 1080;

    public static int getHeight(int px) {
        int screenWidth = BaseActivity.getScreenWidth();
        int screenHeight = BaseActivity.getScreenHeight();
        if (screenWidth == WIDTH_1080 && screenHeight != HEIGHT_1920) {
            screenHeight = HEIGHT_1920;

        }

        if (screenWidth == WIDTH_720 && screenHeight != HEIGHT_1280) {
            screenHeight = HEIGHT_1280;
        }
        double iPhoneProportion = px / (double) (HEIGHT / (double) 100);
        int result = (int) ((screenHeight / (double) 100) * iPhoneProportion);
        return result;

    }

    public static int getWidth(int px) {
        double iPhoneProportion = px / (double) (WEIGHT / (double) 100);
        int result = (int) ((BaseActivity.getScreenWidth() / (double) 100) * iPhoneProportion);
        return result;
    }

    public static int getWidth(int height, int width) {
        float proportion = (height / (float) width);
        return (int) (height / proportion);
    }

    public static void setTextSize(TextView view, int px) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, getWidth(px));
    }

    public static void setTextSize(EditText view, int px) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, getWidth(px));
    }

    public static void setTextSize(Button view, int px) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, getWidth(px));
    }

    public static void setTextSize(RadioButton view, int px) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, getWidth(px));
    }

    public static void setViewSize(View view, int height, int width) {
        if (view != null) {
            if (view.getLayoutParams() != null) {
                if (height != 0) {
                    view.getLayoutParams().height = getHeight(height);
                }

                if (width != 0) {
                    view.getLayoutParams().width = getWidth(width);
                }

            } else {
                LayoutParams params = new LayoutParams(getWidth(width),
                        getHeight(height));
                view.setLayoutParams(params);
            }
        } else {
           
        }
    }

    public static void setViewSize(View view, int height, int width, int type) {
        if (view != null) {
            if (view.getLayoutParams() != null) {
                if (height != 0) {
                    view.getLayoutParams().height = getHeight(height);
                }

                if (width != 0) {
                    view.getLayoutParams().width = getWidth(width);
                }

            } else {

                if (type == LINEARLAYOUT) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            getWidth(width), getHeight(height));
                    view.setLayoutParams(params);
                }

                if (type == RELATIVELAYOUT) {
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            getWidth(width), getHeight(height));
                    view.setLayoutParams(params);
                }
            }

        } else {
            
        }
    }

    public static void setMarginLeft(View view, int margin, int type) {
        if (type == RELATIVELAYOUT) {

            if (view.getLayoutParams() == null) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.leftMargin = getWidth(margin);
                view.setLayoutParams(params);
            } else {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                params.leftMargin = getWidth(margin);
            }

        }
        if (type == LINEARLAYOUT) {

            if (view.getLayoutParams() == null) {
                LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.leftMargin = getWidth(margin);
                view.setLayoutParams(params);
            } else {
                LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) view
                        .getLayoutParams();
                params.leftMargin = getWidth(margin);
            }

        }
    }

    public static void setMarginRight(View view, int margin, int type) {
        if (type == RELATIVELAYOUT) {
            if (view.getLayoutParams() == null) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.rightMargin = getWidth(margin);
                view.setLayoutParams(params);
            } else {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                params.rightMargin = getWidth(margin);
            }
        }
        if (type == LINEARLAYOUT) {

            if (view.getLayoutParams() == null) {
                LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.rightMargin = getWidth(margin);
                view.setLayoutParams(params);
            } else {
                LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) view
                        .getLayoutParams();
                params.rightMargin = getWidth(margin);
            }

        }
    }

    public static void setMargin(View view, int margin, int type) {
        setMarginTop(view, margin, type);
        setMarginBottom(view, margin, type);
        setMarginLeft(view, margin, type);
        setMarginRight(view, margin, type);
    }

    public static void setMargin(View view, int marginTB, int marginLR, int type) {

        if (marginTB != 0) {
            setMarginTop(view, marginTB, type);
            setMarginBottom(view, marginTB, type);
        }

        if (marginLR != 0) {
            setMarginLeft(view, marginLR, type);
            setMarginRight(view, marginLR, type);
        }
    }

    public static void setMargin(View view, int marginT, int marginR,
                                 int marginB, int marginL, int type) {
        if (marginT != 0) {
            setMarginTop(view, marginT, type);
        }

        if (marginR != 0) {
            setMarginRight(view, marginR, type);
        }

        if (marginB != 0) {
            setMarginBottom(view, marginB, type);
        }

        if (marginL != 0) {
            setMarginLeft(view, marginL, type);
        }
    }

    public static void setMarginTop(View view, int margin, int type) {
        if (type == RELATIVELAYOUT) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view
                    .getLayoutParams();
            params.topMargin = getHeight(margin);
        }
        if (type == LINEARLAYOUT) {
            LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) view
                    .getLayoutParams();
            params.topMargin = getHeight(margin);
        }
    }

    public static void setPadding(View view, int paddingTB, int paddingLR) {
        if (paddingTB != 0) {
            setPaddingTop(view, paddingTB);
            setPaddingBottom(view, paddingTB);
        }
        if (paddingLR != 0) {
            setPaddingLeft(view, paddingLR);
            setPaddingRight(view, paddingLR);
        }
    }

    public static void setMarginBottom(View view, int margin, int type) {
        if (type == RELATIVELAYOUT) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view
                    .getLayoutParams();
            params.bottomMargin = getHeight(margin);
        }
        if (type == LINEARLAYOUT) {
            LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) view
                    .getLayoutParams();
            params.bottomMargin = getHeight(margin);
        }
    }

    public static void setPadding(View view, int paddingTop, int paddingRight,
                                  int paddingBottom, int paddingLeft) {

        if (paddingTop != 0) {
            setPaddingTop(view, paddingTop);
        }

        if (paddingRight != 0) {
            setPaddingRight(view, paddingRight);
        }

        if (paddingBottom != 0) {
            setPaddingBottom(view, paddingBottom);
        }

        if (paddingLeft != 0) {
            setPaddingLeft(view, paddingLeft);
        }
    }

    public static void setPadding(View view, int padding) {
        setPaddingTop(view, padding);
        setPaddingBottom(view, padding);
        setPaddingLeft(view, padding);
        setPaddingRight(view, padding);
    }

    public static void setPaddingLeft(View view, int padding) {
        view.setPadding(getWidth(padding), view.getPaddingTop(),
                view.getPaddingRight(), view.getPaddingBottom());
    }

    public static void setPaddingRight(View view, int padding) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(),
                getWidth(padding), view.getPaddingBottom());
    }

    public static void setPaddingTop(View view, int padding) {
        view.setPadding(view.getPaddingLeft(), getWidth(padding),
                view.getPaddingRight(), view.getPaddingBottom());
    }

    public static void setPaddingBottom(View view, int padding) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(),
                view.getPaddingRight(), getWidth(padding));
    }

    public static void setDrawablePadding(TextView view, int padding) {
        view.setCompoundDrawablePadding(getWidth(padding));
    }

}