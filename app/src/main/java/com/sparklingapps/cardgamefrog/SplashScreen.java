package com.sparklingapps.cardgamefrog;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.sparklingapps.cardgamefrog.utils.AppConstants;
import com.sparklingapps.cardgamefrog.utils.PreferenceController;

public class SplashScreen extends AppCompatActivity {

    private PreferenceController sharedPreferenceController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferenceController = new PreferenceController(SplashScreen.this);
        boolean registerStatus =sharedPreferenceController.getBoolean(AppConstants.PLAYER_REGISTER);

        if(registerStatus) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashScreen.this, MainActivity.class));

                }
            }, 1000);
        }
        else{

            startActivity(new Intent(SplashScreen.this, RegisterPlayer.class));

        }
    }
}
