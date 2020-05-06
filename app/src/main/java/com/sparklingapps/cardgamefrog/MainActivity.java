package com.sparklingapps.cardgamefrog;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.heroiclabs.nakama.Client;
import com.sparklingapps.cardgamefrog.interfaces.SocketNetworkInterface;
import com.sparklingapps.cardgamefrog.model.CreateRoom;
import com.sparklingapps.cardgamefrog.model.JoinRoom;
import com.sparklingapps.cardgamefrog.model.Player;
import com.sparklingapps.cardgamefrog.model.Room;
import com.sparklingapps.cardgamefrog.socket.SocketConstants;
import com.sparklingapps.cardgamefrog.socket.SocketNetworkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity implements SocketNetworkInterface {
    //socket connection variables
    private SocketNetworkManager socketConnection;
    private static final String TAG = "MainActivity";
    private Socket socket;

    private Room roomId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createGameBtn = findViewById(R.id.btn_register);
        createGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Player playerObj = new Player();
                playerObj.setName("BASIL");
                playerObj.setMyTurn(false);
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
                playerObj.setMyTurn(false);
                playerObj.setType("USER");

                EditText text = findViewById(R.id.room_id);
                JoinRoom joinRoom = new JoinRoom();
                joinRoom.setRoomId(Integer.parseInt(text.getText().toString()));
                joinRoom.setPlayerObj(playerObj);
                Gson gson = new Gson();
                String joinrequest = gson.toJson(joinRoom);
                socketConnection.registerEventListenerHandler(SocketConstants.NOTIFY_PLAYER_JOIN);
                socketConnection.sendDataToServer(SocketConstants.PLAYER_JOIN,joinrequest);

            }
        });


        Button btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                String test = gson.toJson(roomId);
                socketConnection.registerEventListenerHandler(SocketConstants.ADD_CARD);
                socketConnection.sendDataToServer(SocketConstants.START_GAME,test);


            }
        });


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
    public void addCardToPlayerHand(String responseMsg) {

        Log.d(TAG, "addCardToPlayerHand: "+responseMsg);

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
}
