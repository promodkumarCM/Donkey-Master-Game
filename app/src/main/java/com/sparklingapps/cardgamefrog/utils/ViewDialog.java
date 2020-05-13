package com.sparklingapps.cardgamefrog.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sparklingapps.cardgamefrog.R;
import com.sparklingapps.cardgamefrog.enums.GameStartEnum;
import com.sparklingapps.cardgamefrog.interfaces.onClickDialog;
import com.sparklingapps.cardgamefrog.model.Room;


public class ViewDialog {

    public void showCreateGameDialog(Activity activity, int sharePin, Room roomId, onClickDialog listener) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_create_game_pop);
        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        CardGameTextView tv_sharePin = dialog.findViewById(R.id.txt_share_pin);
        tv_sharePin.setText("" + sharePin);

        ImageView close =  dialog.findViewById(R.id.iv_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        CardView btnStartEvent = dialog.findViewById(R.id.btn_play_now);
        btnStartEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClickDialogStartGame(roomId, GameStartEnum.START);
                }
            }
        });
        dialog.show();
    }


    public void showJoinGameDialog(Activity activity,onClickDialog listener) {

        final Dialog dialogjoin = new Dialog(activity);
        dialogjoin.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogjoin.setContentView(R.layout.dialog_join_game);
        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView close =  dialogjoin.findViewById(R.id.iv_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogjoin.dismiss();
            }
        });
        CardGameEditText ed_Pin =  dialogjoin.findViewById(R.id.edt_join_pin);

        CardView btnStartEvent =  dialogjoin.findViewById(R.id.btn_create_event);
        btnStartEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pin = ed_Pin.getText().toString();
                if (listener != null && !pin.equalsIgnoreCase("")) {
                    listener.onClickDialogJoinGame(Integer.parseInt(pin),GameStartEnum.JOIN);
                }
            }
        });

        dialogjoin.show();

    }


    public static void showErrorMsg(Activity activity,String msg) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.error_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }




}