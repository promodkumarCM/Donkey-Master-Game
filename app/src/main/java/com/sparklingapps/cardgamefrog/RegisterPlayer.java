package com.sparklingapps.cardgamefrog;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.sparklingapps.cardgamefrog.utils.AppConstants;
import com.sparklingapps.cardgamefrog.utils.CardGameEditText;
import com.sparklingapps.cardgamefrog.utils.PreferenceController;
import com.sparklingapps.cardgamefrog.utils.ViewDialog;


public class RegisterPlayer extends AppCompatActivity {

    private CardGameEditText ed_Email, ed_Name;
    private FrameLayout frmRegisterBtn;
    private PreferenceController sharedPreferenceController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_player_2);

        ed_Email = findViewById(R.id.edt_email);
        ed_Name = findViewById(R.id.edt_name);

        frmRegisterBtn = findViewById(R.id.frm_register_btn);
        frmRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = ed_Email.getText().toString();
                String name = ed_Name.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(email)) {

                    ViewDialog.showErrorMsg(RegisterPlayer.this, "Should Enter Details in field");

                } else {
                    sharedPreferenceController = new PreferenceController(RegisterPlayer.this);
                    sharedPreferenceController.setString(AppConstants.PLAYER_NAME, name);
                    sharedPreferenceController.setString(AppConstants.PLAYER_ROOM, email + "_" + name);
                    sharedPreferenceController.setBoolean(AppConstants.PLAYER_REGISTER, true);

                    Intent intent = new Intent(RegisterPlayer.this, MainActivity.class);
                    startActivity(intent);

                }

            }
        });


    }
}
