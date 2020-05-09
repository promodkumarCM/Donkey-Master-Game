package com.sparklingapps.cardgamefrog.utils;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public static int screenWidth;
    public static int screenHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenWidth = getWindow().getDecorView().getRootView().getWidth();
        screenHeight = getWindow().getDecorView().getRootView().getHeight();
    }

    public static int getScreenWidth(){
        return screenWidth;
    }

    public static int getScreenHeight(){
        return screenHeight;
    }
}
