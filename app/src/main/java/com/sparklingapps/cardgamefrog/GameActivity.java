package com.sparklingapps.cardgamefrog;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sparklingapps.cardgamefrog.adapters.MyCardAdapter;
import com.sparklingapps.cardgamefrog.adapters.MyTableCardAdapter;
import com.sparklingapps.cardgamefrog.enums.Cards;
import com.sparklingapps.cardgamefrog.enums.Suit;
import com.sparklingapps.cardgamefrog.interfaces.OnCardAdapterClick;
import com.sparklingapps.cardgamefrog.interfaces.SocketNetworkInterface;
import com.sparklingapps.cardgamefrog.model.CardListAndAdapterModel;
import com.sparklingapps.cardgamefrog.model.Hand;
import com.sparklingapps.cardgamefrog.model.Player;
import com.sparklingapps.cardgamefrog.socket.SocketConstants;
import com.sparklingapps.cardgamefrog.socket.SocketNetworkManager;
import com.sparklingapps.cardgamefrog.utils.BaseActivity;
import com.sparklingapps.cardgamefrog.utils.HorizontalOverlapDecoration;
import com.sparklingapps.cardgamefrog.utils.VerticalOverlapDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import io.socket.client.Socket;

public class GameActivity extends BaseActivity implements SocketNetworkInterface, OnCardAdapterClick {

    private static final String TAG = "GameActivity";
    private SocketNetworkManager socketConnection;
    private Socket socket;
    private RecyclerView mDeckCardRecyclerView, mClubsCardRecyclerView, mDiamondsCardRecyclerView, mHeartsCardRecyclerView, mSpadesCardRecyclerView;
    private ArrayList<Integer> mTableCardList;
    private ArrayList<Player> mTablePlayerList;
    private ArrayList<Integer> mClubsCardList;
    private ArrayList<Integer> mDiamondsCardList;
    private ArrayList<Integer> mHeartsCardList;
    private ArrayList<Integer> mSpadesCardList;
    private MyTableCardAdapter mTableCardAdapter;
    private HorizontalOverlapDecoration itemDecorator;
    private VerticalOverlapDecoration verticalItemDecorator;
    private ImageView selectedCardImageView;
    private Integer selectedCardObj;
    private HashMap mHashMapWithAdapter;
    private CardView btnDeal;
    private OnCardAdapterClick cardClickListener;
    private Player playerObjFromServer;
    private TextView tv_tableMsg;
    private Hand hand;
    public static Integer totalPlayerSlot = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tv_tableMsg = findViewById(R.id.table_msg);
        btnDeal = findViewById(R.id.btn_deal);

        init_sockets();

        Intent intent = getIntent();
        final String playerJsonString = intent.getStringExtra("playerJson");
        playerObjFromServer = new Gson().fromJson(playerJsonString, Player.class);
        totalPlayerSlot = playerObjFromServer.getTotalPlayers();

        HashSet<Integer> hashSet = new HashSet<Integer>();
        ArrayList<Integer> test = new ArrayList<>();
        test.addAll(playerObjFromServer.getCard());
        hashSet.addAll(test);
        test.clear();
        test.addAll(hashSet);

        hand = new Hand();
        hand.buildHand(test);

        mTableCardList = new ArrayList<>();
        mTablePlayerList = new ArrayList<>();
        //each suit lists
        mClubsCardList = hand.getCardsBySuit(Suit.CLUBS);
        mDiamondsCardList = hand.getCardsBySuit(Suit.DIAMONDS);
        mHeartsCardList = hand.getCardsBySuit(Suit.HEARTS);
        mSpadesCardList = hand.getCardsBySuit(Suit.SPADES);

        selectedCardImageView = findViewById(R.id.iv_selectedCard);
        cardClickListener = this;


        btnDeal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //setting current dealed card to table & notify data change

               btnDeal.setVisibility(View.INVISIBLE);

                ArrayList<Integer> currentPlayedCard = new ArrayList<>();
                currentPlayedCard.add(selectedCardObj);

                Player roundPlay;
                roundPlay = playerObjFromServer;
                roundPlay.setCard(currentPlayedCard);

                Gson gson = new Gson();
                String roundPlayedJson = gson.toJson(roundPlay);

                socketConnection.sendDataToServer(SocketConstants.TABLE_ROUND, roundPlayedJson);

                String cardSuitName = Cards.valueOf(selectedCardObj).name().substring(0, Cards.valueOf(selectedCardObj).name().indexOf("_"));
                hand.removeOneCardInHand(selectedCardObj);

                //reBuildHand(hand.getCardsInHand(),cardSuitName);
                reBuildHand(hand.getCardsInHand(),"");


            }
        });



        //setting recycler view with my [players] card //full card horizontal
        mTableCardAdapter = new MyTableCardAdapter(GameActivity.this, mTableCardList, mTablePlayerList);
        mDeckCardRecyclerView = findViewById(R.id.rv_table_deck_cards);
        mDeckCardRecyclerView.setLayoutManager(new LinearLayoutManager(GameActivity.this, LinearLayoutManager.HORIZONTAL, false));
        mDeckCardRecyclerView.setAdapter(mTableCardAdapter);

        mHashMapWithAdapter = new HashMap();
        mHashMapWithAdapter.put(Suit.CLUBS.name(), new CardListAndAdapterModel(mClubsCardList, new MyCardAdapter(GameActivity.this, mClubsCardList, cardClickListener)));
        mHashMapWithAdapter.put(Suit.DIAMONDS.name(), new CardListAndAdapterModel(mDiamondsCardList, new MyCardAdapter(GameActivity.this, mDiamondsCardList, cardClickListener)));
        mHashMapWithAdapter.put(Suit.HEARTS.name(), new CardListAndAdapterModel(mHeartsCardList, new MyCardAdapter(GameActivity.this, mHeartsCardList, cardClickListener)));
        mHashMapWithAdapter.put(Suit.SPADES.name(), new CardListAndAdapterModel(mSpadesCardList, new MyCardAdapter(GameActivity.this, mSpadesCardList, cardClickListener)));


        verticalItemDecorator = new VerticalOverlapDecoration();


        mClubsCardRecyclerView = findViewById(R.id.rv_clubs);
        mClubsCardRecyclerView.addItemDecoration(verticalItemDecorator);
        LinearLayoutManager manager = new LinearLayoutManager(GameActivity.this, LinearLayoutManager.VERTICAL, false);
        manager.setStackFromEnd(true);
        mClubsCardRecyclerView.setLayoutManager(manager);
        mClubsCardRecyclerView.setAdapter(((CardListAndAdapterModel) mHashMapWithAdapter.get(Suit.CLUBS.name())).getmAdapter());

        mDiamondsCardRecyclerView = findViewById(R.id.rv_diamonds);
        mDiamondsCardRecyclerView.addItemDecoration(verticalItemDecorator);
        LinearLayoutManager manager1 = new LinearLayoutManager(GameActivity.this, LinearLayoutManager.VERTICAL, false);
        manager.setStackFromEnd(true);
        mDiamondsCardRecyclerView.setLayoutManager(manager1);
        mDiamondsCardRecyclerView.setAdapter(((CardListAndAdapterModel) mHashMapWithAdapter.get(Suit.DIAMONDS.name())).getmAdapter());

        mHeartsCardRecyclerView = findViewById(R.id.rv_hearts);
        mHeartsCardRecyclerView.addItemDecoration(verticalItemDecorator);
        LinearLayoutManager manager2 = new LinearLayoutManager(GameActivity.this, LinearLayoutManager.VERTICAL, false);
        manager.setStackFromEnd(true);
        mHeartsCardRecyclerView.setLayoutManager(manager2);
        mHeartsCardRecyclerView.setAdapter(((CardListAndAdapterModel) mHashMapWithAdapter.get(Suit.HEARTS.name())).getmAdapter());

        mSpadesCardRecyclerView = findViewById(R.id.rv_spades);
        mSpadesCardRecyclerView.addItemDecoration(verticalItemDecorator);
        LinearLayoutManager manager3 = new LinearLayoutManager(GameActivity.this, LinearLayoutManager.VERTICAL, false);
        manager.setStackFromEnd(true);
        mSpadesCardRecyclerView.setLayoutManager(manager3);
        mSpadesCardRecyclerView.setAdapter(((CardListAndAdapterModel) mHashMapWithAdapter.get(Suit.SPADES.name())).getmAdapter());


        if (playerObjFromServer.getIsMyTurn()) {

            tv_tableMsg.setText("Your Turn");
            btnDeal.setVisibility(View.VISIBLE);
            checkCardPresentForUser(true);
            //Toast.makeText(this, "Your TURN", Toast.LENGTH_LONG).show();

        }


    }

    public void showButtonAnimation(){
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
        btnDeal.startAnimation(animShake);
    }

    public void reBuildHand(ArrayList<Integer> arrayAfter,String suitToNotify) {
        if (hand != null) {
            hand.buildHand(arrayAfter);
            mClubsCardList = hand.getCardsBySuit(Suit.CLUBS);
            mDiamondsCardList = hand.getCardsBySuit(Suit.DIAMONDS);
            mHeartsCardList = hand.getCardsBySuit(Suit.HEARTS);
            mSpadesCardList = hand.getCardsBySuit(Suit.SPADES);
            if(TextUtils.isEmpty(suitToNotify)){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ((CardListAndAdapterModel) mHashMapWithAdapter.get(Suit.CLUBS.name())).getmAdapter().notifyDataSetChanged();
                        ((CardListAndAdapterModel) mHashMapWithAdapter.get(Suit.DIAMONDS.name())).getmAdapter().notifyDataSetChanged();
                        ((CardListAndAdapterModel) mHashMapWithAdapter.get(Suit.HEARTS.name())).getmAdapter().notifyDataSetChanged();
                        ((CardListAndAdapterModel) mHashMapWithAdapter.get(Suit.SPADES.name())).getmAdapter().notifyDataSetChanged();

                    }
                });

            }else {
                ((CardListAndAdapterModel) mHashMapWithAdapter.get(Suit.valueOf(suitToNotify).name())).getmAdapter().notifyDataSetChanged();
            }

        }
    }


    public void init_sockets() {

        socketConnection = SocketNetworkManager.getInstance();
        socket = socketConnection.getSocket();
        if (socket == null) {
            socketConnection.connectToSocket();
        }
        socketConnection.addSocketNetworkInterface(this);
        socketConnection.registerEventListenerHandler(SocketConstants.TURN_UPDATE);
        socketConnection.registerEventListenerHandler(SocketConstants.ADD_CARD);
        socketConnection.registerEventListenerHandler(SocketConstants.ADD_CARD_TABLE);
        socketConnection.registerEventListenerHandler(SocketConstants.CLEAR_TABLE);
    }


    @Override
    public void networkCallbackReceived() {

    }

    @Override
    public void onNewMessageReceived(String responseMsg) {

    }

    @Override
    public void onConnected(boolean connected) {

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

    @Override
    public void onAfterGameCreated(String responseMsg) {

    }

    @Override
    public void notifyPlayerJoin(String responseMsg) {
    }

    @Override
    public void addCardToPlayerHand(String responseMsg) {

        Log.d(TAG, "addCardToPlayerHand: "+responseMsg);
       Player addCardToPlayer = new Gson().fromJson(responseMsg, Player.class);
       //todo
        hand.addCardToHand(addCardToPlayer.getCard());
        reBuildHand(hand.getCardsInHand(),"");
    }

    @Override
    public void onGameStarted(String responseMsg) {
    }

    @Override
    public void onClearTable(String responseMsg) {

        mTableCardList.clear();
        mTablePlayerList.clear();


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                selectedCardImageView.setImageResource(android.R.color.transparent);
                mTableCardAdapter.notifyDataSetChanged();
                tv_tableMsg.setText("TABLE CLEARED");
            }
        });

    }

    @Override
    public void updateTurn(final boolean myTurn) {

        runOnUiThread(
                new Runnable() {
            @Override
            public void run() {

                tv_tableMsg.setText("Your Turn");
                btnDeal.setVisibility(View.VISIBLE);
                showButtonAnimation();
                if(mTableCardList.size() <= 0 && myTurn ) {
                    enableAllSuitRecyclerView();
                }

            }
        });

    }

    @Override
    public void addCardToTable(String responseMsg) {

        Log.d(TAG, "addCardToTable: " + responseMsg);
        Player currentCardPutplayer = new Gson().fromJson(responseMsg, Player.class);
        mTableCardList.addAll(currentCardPutplayer.getCard());
        mTablePlayerList.add(currentCardPutplayer);
        //removing duplicated
/*        HashSet<Integer> hashSet = new HashSet<Integer>();
        hashSet.addAll(mTableCardList);
        mTableCardList.clear();
        mTableCardList.addAll(hashSet);*/
        //notfy adapter data change
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_tableMsg.setText("");
                selectedCardImageView.setImageResource(android.R.color.transparent);
                mTableCardAdapter.notifyDataSetChanged();
                checkCardPresentForUser(false);
            }
        });

    }

    public void checkCardPresentForUser(boolean firstTime) {
        int card = 51;
        if(!firstTime){
            card = mTableCardList.get(mTableCardList.size()-1);
        }

        Suit suit =Cards.getClub(card);
        int sameSuitCardsLength = hand.getCardSizeBySuit(suit);
        if(sameSuitCardsLength > 0){
            Iterator iterator = mHashMapWithAdapter.entrySet().iterator();
            while (iterator.hasNext()){
                HashMap.Entry me2 = (HashMap.Entry) iterator.next();
                if(suit.name().equalsIgnoreCase(me2.getKey().toString())){
                    MyCardAdapter tempAdapter = ((CardListAndAdapterModel) me2.getValue()).getmAdapter();
                    tempAdapter.setIsClickable(true);
                    tempAdapter.notifyDataSetChanged();
                }
                else{
                    MyCardAdapter tempAdapter = ((CardListAndAdapterModel) me2.getValue()).getmAdapter();
                    tempAdapter.setIsClickable(false);
                    tempAdapter.notifyDataSetChanged();
                }
            }
        }
        else{
            //user not hand that suit of card
            enableAllSuitRecyclerView();
        }
    }

    public void enableAllSuitRecyclerView(){

        Iterator iterator = mHashMapWithAdapter.entrySet().iterator();
        while (iterator.hasNext()) {
            HashMap.Entry me2 = (HashMap.Entry) iterator.next();
            MyCardAdapter tempAdapter = ((CardListAndAdapterModel) me2.getValue()).getmAdapter();
            tempAdapter.setIsClickable(true);
            tempAdapter.notifyDataSetChanged();
        }

    }




    @Override
    public void userSelectedCard(Integer card) {


        selectedCardObj = card;
        selectedCardImageView.setImageResource(Cards.valueOf(card).getResource());
        String cardSuitName = Cards.valueOf(card).name().substring(0, Cards.valueOf(card).name().indexOf("_"));
        ((CardListAndAdapterModel) mHashMapWithAdapter.get(Suit.valueOf(cardSuitName).name())).getmAdapter().notifyDataSetChanged();


    }
}
