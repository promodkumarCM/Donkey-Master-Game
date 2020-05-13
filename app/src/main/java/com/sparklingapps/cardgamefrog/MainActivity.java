package com.sparklingapps.cardgamefrog;


import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import com.sparklingapps.cardgamefrog.enums.GameStartEnum;
import com.sparklingapps.cardgamefrog.fragments.HomeFragment;
import com.sparklingapps.cardgamefrog.interfaces.SocketNetworkInterface;
import com.sparklingapps.cardgamefrog.interfaces.onClickDialog;
import com.sparklingapps.cardgamefrog.model.CreateRoom;
import com.sparklingapps.cardgamefrog.model.JoinRoom;
import com.sparklingapps.cardgamefrog.model.Player;
import com.sparklingapps.cardgamefrog.model.Room;
import com.sparklingapps.cardgamefrog.socket.SocketConstants;
import com.sparklingapps.cardgamefrog.socket.SocketNetworkManager;
import com.sparklingapps.cardgamefrog.utils.AppConstants;
import com.sparklingapps.cardgamefrog.utils.BaseActivity;
import com.sparklingapps.cardgamefrog.utils.MyHelper;
import com.sparklingapps.cardgamefrog.utils.PreferenceController;
import com.sparklingapps.cardgamefrog.utils.ViewDialog;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import io.socket.client.Socket;

public class MainActivity extends BaseActivity implements SocketNetworkInterface, onClickDialog {
    //socket connection variables
    private SocketNetworkManager socketConnection;
    private static final String TAG = "MainActivity";
    private Socket socket;
    private Room roomId;

    private PreferenceController sharedPreferenceController;
    private CardView btnCreateEvent;
    private CardView btnJoinEvent;
    private onClickDialog dialogClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_new);

        sharedPreferenceController = new PreferenceController(MainActivity.this);

        dialogClickListener = this;
        btnCreateEvent = findViewById(R.id.btn_create_event);
        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                MyHelper.showProgress(MainActivity.this,false);

                Player playerObj = new Player();
                playerObj.setName(sharedPreferenceController.getString(AppConstants.PLAYER_NAME));
                playerObj.setIsMyTurn(false);
                playerObj.setType("admin");

                CreateRoom roomObj = new CreateRoom();
                roomObj.setRoomName(sharedPreferenceController.getString(AppConstants.PLAYER_ROOM));
                roomObj.setRoomTotalCount(2);
                roomObj.setPlayerObj(playerObj);


                Gson gson = new Gson();
                try {
                    String createRoomJson = gson.toJson(roomObj);
                    socketConnection.registerEventListenerHandler(SocketConstants.AFTER_GAME_CREATED);
                    socketConnection.registerEventListenerHandler(SocketConstants.NOTIFY_PLAYER_JOIN);
                    socketConnection.sendDataToServer(SocketConstants.CREATE_GAME,createRoomJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        btnJoinEvent = findViewById(R.id.btn_join_event);
        btnJoinEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewDialog dialog = new ViewDialog();
                dialog.showJoinGameDialog(MainActivity.this,dialogClickListener);


            }
        });





     /*   Button createGameBtn = findViewById(R.id.btn_register);
        createGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Player playerObj = new Player();
                playerObj.setName("BASIL");
                playerObj.setIsMyTurn(false);
                playerObj.setType("admin");

                CreateRoom roomObj = new CreateRoom();
                roomObj.setRoomName("Sample_Room");
                roomObj.setRoomTotalCount(2);
                roomObj.setPlayerObj(playerObj);

                Gson gson = new Gson();
                try {
                    String createRoomJson = gson.toJson(roomObj);
                    socketConnection.registerEventListenerHandler(SocketConstants.AFTER_GAME_CREATED);
                    socketConnection.registerEventListenerHandler(SocketConstants.NOTIFY_PLAYER_JOIN);
                    socketConnection.sendDataToServer(SocketConstants.CREATE_GAME,createRoomJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        Button btnJoinGame = findViewById(R.id.btn_join);
        btnJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Player playerObj = new Player();
                playerObj.setName("PROMOD");
                playerObj.setIsMyTurn(false);
                playerObj.setType("USER");

                EditText text = findViewById(R.id.room_id);
                JoinRoom joinRoom = new JoinRoom();
                joinRoom.setRoomId(Integer.parseInt(text.getText().toString()));
                joinRoom.setPlayerObj(playerObj);
                Gson gson = new Gson();
                String joinrequest = gson.toJson(joinRoom);
                socketConnection.registerEventListenerHandler(SocketConstants.NOTIFY_PLAYER_JOIN);
                socketConnection.registerEventListenerHandler(SocketConstants.STARTED_GAME);
                socketConnection.sendDataToServer(SocketConstants.PLAYER_JOIN,joinrequest);

            }
        });


        final Button btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnStart.setEnabled(false);
                Gson gson = new Gson();
                String test = gson.toJson(roomId);
               // socketConnection.registerEventListenerHandler(SocketConstants.ADD_CARD);
                socketConnection.registerEventListenerHandler(SocketConstants.STARTED_GAME);
                socketConnection.sendDataToServer(SocketConstants.START_GAME,test);



            }
        });


         Button btn_frag=findViewById(R.id.btn_frag);
         btn_frag.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 loadFragment(new HomeFragment());
             }
         });*/



    }

    @Override
    protected void onResume() {
        super.onResume();
        socketConnection = null;
        init_sockets();
    }

    public void init_sockets() {

        socketConnection = SocketNetworkManager.getInstance();
        socket = socketConnection.getSocket();
        if (socket == null) {
            socketConnection.connectToSocket();
        }
        socketConnection.addSocketNetworkInterface(this);
        socketConnection.registerEventListenerHandler(SocketConstants.ON_CONNECTION);

    }




    @Override
    public void networkCallbackReceived() {

    }

    @Override
    public void onConnected(boolean connected) {
        Log.d(TAG, "onConnected: "+connected);
    }

    @Override
    public void onAfterGameCreated(String responseMsg) {
        Gson gson = new Gson();
        Room room = gson.fromJson(responseMsg,Room.class);
        roomId = room;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyHelper.hideProgress();
                ViewDialog dialog = new ViewDialog();
                dialog.showCreateGameDialog(MainActivity.this,room.getRoomId(),roomId,dialogClickListener);

            }
        });


        Log.d(TAG, "onAfterGameCreated: "+room.getRoomId());

    }

    @Override
    public void notifyPlayerJoin(final String responseMsg) {
        
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, responseMsg, Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    @Override
    public void addCardToPlayerHand(final String responseMsg) {

        Log.d(TAG, "addCardToPlayerHand: "+responseMsg);

    }

    @Override
    public void addCardToTable(String responseMsg) {

        Log.d(TAG, "addCardToTable: "+responseMsg);
    }

    @Override
    public void onGameStarted(String responseMsg) {

        Intent intent = new Intent(MainActivity.this,GameActivity.class);
        intent.putExtra("playerJson",responseMsg);
        SocketNetworkManager.removeSocketNetworkInterface(this);
        startActivity(intent);

    }

    @Override
    public void onClearTable(String responseMsg) {

    }

    @Override
    public void updateTurn(boolean myTurn) {

    }

    @Override
    public void onNewMessageReceived(String responseMsg) {

        Log.d(TAG, "onNewMessageReceived: "+responseMsg);


    }

    @Override
    public void onDisconnected(boolean disConnected) {

    }

    @Override
    public void onConnectionTimeOut(boolean status) {

    }

    @Override
    public void onSocketError() {

    }


    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm=getSupportFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }


    @Override
    public void onClickDialogStartGame(Room roomId, GameStartEnum gameCase) {
        Gson gson = new Gson();
        String test = gson.toJson(roomId);
        // socketConnection.registerEventListenerHandler(SocketConstants.ADD_CARD);
        socketConnection.registerEventListenerHandler(SocketConstants.STARTED_GAME);
        socketConnection.sendDataToServer(SocketConstants.START_GAME,test);

        MyHelper.showProgress(MainActivity.this,false);

    }

    @Override
    public void onClickDialogJoinGame(int roomId, GameStartEnum gameCase) {

        MyHelper.showProgress(MainActivity.this,false);

        Player playerObj = new Player();
        playerObj.setName(sharedPreferenceController.getString(AppConstants.PLAYER_NAME));
        playerObj.setIsMyTurn(false);
        playerObj.setType("USER");


        JoinRoom joinRoom = new JoinRoom();
        joinRoom.setRoomId(roomId);
        joinRoom.setPlayerObj(playerObj);
        Gson gson = new Gson();

        String joinrequest = gson.toJson(joinRoom);

        socketConnection.registerEventListenerHandler(SocketConstants.NOTIFY_PLAYER_JOIN);
        socketConnection.registerEventListenerHandler(SocketConstants.STARTED_GAME);
        socketConnection.sendDataToServer(SocketConstants.PLAYER_JOIN,joinrequest);



    }
}
