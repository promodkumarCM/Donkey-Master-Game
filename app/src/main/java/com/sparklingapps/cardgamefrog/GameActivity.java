package com.sparklingapps.cardgamefrog;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sparklingapps.cardgamefrog.adapters.MyCardAdapter;
import com.sparklingapps.cardgamefrog.enums.Suit;
import com.sparklingapps.cardgamefrog.interfaces.OnCardAdapterClick;
import com.sparklingapps.cardgamefrog.interfaces.SocketNetworkInterface;
import com.sparklingapps.cardgamefrog.model.Card;
import com.sparklingapps.cardgamefrog.model.CardListAndAdapterModel;
import com.sparklingapps.cardgamefrog.model.Hand;
import com.sparklingapps.cardgamefrog.model.Player;
import com.sparklingapps.cardgamefrog.utils.HorizontalOverlapDecoration;
import com.sparklingapps.cardgamefrog.utils.VerticalOverlapDecoration;

import java.util.ArrayList;
import java.util.HashMap;

public class GameActivity extends AppCompatActivity implements SocketNetworkInterface, OnCardAdapterClick {

    private static final String TAG = "GameActivity";
    private RecyclerView mDeckCardRecyclerView,mClubsCardRecyclerView,mDiamondsCardRecyclerView,mHeartsCardRecyclerView,mSpadesCardRecyclerView;
    private ArrayList<Card> mCardList;
    private ArrayList<Card> mClubsCardList;
    private ArrayList<Card> mDiamondsCardList;
    private ArrayList<Card> mHeartsCardList;
    private ArrayList<Card> mSpadesCardList;
    private MyCardAdapter mCardAdapter;
    private HorizontalOverlapDecoration itemDecorator;
    private VerticalOverlapDecoration verticalItemDecorator;
    private ImageView selectedCardImageView;
    private Card selectedCardObj;
    private HashMap mHashMapWithAdapter;
    private Button btnDeal ;

    private OnCardAdapterClick cardClickListener;

    private  Player playerObjFromServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        final String playerJsonString = intent.getStringExtra("playerJson");
        playerObjFromServer = new Gson().fromJson(playerJsonString,Player.class);

        if(playerObjFromServer.getIsMyTurn()){

            Toast.makeText(this, "Your TURN", Toast.LENGTH_LONG).show();
            
        }

        Hand hand = new Hand();
        hand.buildHand(playerObjFromServer.getCard(),GameActivity.this);
        mCardList = hand.getCardsInHand();
        //each suit lists
        mClubsCardList = hand.getCardsBySuit(Suit.CLUBS);
        mDiamondsCardList = hand.getCardsBySuit(Suit.DIAMONDS);
        mHeartsCardList = hand.getCardsBySuit(Suit.HEARTS);
        mSpadesCardList = hand.getCardsBySuit(Suit.SPADES);

        selectedCardImageView = findViewById(R.id.iv_selectedCard);
        cardClickListener = this;

        btnDeal = findViewById(R.id.btn_deal);
        btnDeal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Card> currentPlayedCard = new ArrayList<>();
                currentPlayedCard.add(selectedCardObj);

                Player roundPlay;
                roundPlay = playerObjFromServer;
                roundPlay.setCard(currentPlayedCard);

                Gson gson = new Gson();
                String roundPlayedJson = gson.toJson(roundPlay);




            }
        });

        //setting recycler view with my [players] card //full card horizontal
        mCardAdapter = new MyCardAdapter(GameActivity.this,mCardList);
        itemDecorator = new HorizontalOverlapDecoration();
        mDeckCardRecyclerView = findViewById(R.id.rv_deck_cards);
        mDeckCardRecyclerView.setLayoutManager(new LinearLayoutManager(GameActivity.this,LinearLayoutManager.HORIZONTAL, false));
        mDeckCardRecyclerView.addItemDecoration(itemDecorator);
        mDeckCardRecyclerView.setAdapter(mCardAdapter);

        mHashMapWithAdapter = new HashMap();
        mHashMapWithAdapter.put(Suit.CLUBS.name(),new CardListAndAdapterModel(mClubsCardList,new MyCardAdapter(GameActivity.this,mClubsCardList,cardClickListener)));
        mHashMapWithAdapter.put(Suit.DIAMONDS.name(),new CardListAndAdapterModel(mDiamondsCardList,new MyCardAdapter(GameActivity.this,mDiamondsCardList,cardClickListener)));
        mHashMapWithAdapter.put(Suit.HEARTS.name(),new CardListAndAdapterModel(mHeartsCardList,new MyCardAdapter(GameActivity.this,mHeartsCardList,cardClickListener)));
        mHashMapWithAdapter.put(Suit.SPADES.name(),new CardListAndAdapterModel(mSpadesCardList,new MyCardAdapter(GameActivity.this,mSpadesCardList,cardClickListener)));


        verticalItemDecorator = new VerticalOverlapDecoration();


        mClubsCardRecyclerView = findViewById(R.id.rv_clubs);
        mClubsCardRecyclerView.addItemDecoration(verticalItemDecorator);
        LinearLayoutManager manager = new LinearLayoutManager(GameActivity.this,LinearLayoutManager.VERTICAL, false);
        manager.setStackFromEnd(true);
        mClubsCardRecyclerView.setLayoutManager(manager);
        mClubsCardRecyclerView.setAdapter(((CardListAndAdapterModel)mHashMapWithAdapter.get(Suit.CLUBS.name())).getmAdapter());

        /*mDiamondsCardRecyclerView = findViewById(R.id.rv_diamonds);
        mDiamondsCardRecyclerView.addItemDecoration(verticalItemDecorator);
        LinearLayoutManager manager1 = new LinearLayoutManager(GameActivity.this,LinearLayoutManager.VERTICAL, false);
        manager.setStackFromEnd(true);
        mDiamondsCardRecyclerView.setLayoutManager(manager1);
        mDiamondsCardRecyclerView.setAdapter(((CardListAndAdapterModel)mHashMapWithAdapter.get(Suit.DIAMONDS.name())).getmAdapter());

        mHeartsCardRecyclerView = findViewById(R.id.rv_hearts);
        mHeartsCardRecyclerView.addItemDecoration(verticalItemDecorator);
        LinearLayoutManager manager2 = new LinearLayoutManager(GameActivity.this,LinearLayoutManager.VERTICAL, false);
        manager.setStackFromEnd(true);
        mHeartsCardRecyclerView.setLayoutManager(manager2);
        mHeartsCardRecyclerView.setAdapter(((CardListAndAdapterModel)mHashMapWithAdapter.get(Suit.HEARTS.name())).getmAdapter());

        mSpadesCardRecyclerView = findViewById(R.id.rv_spades);
        mSpadesCardRecyclerView.addItemDecoration(verticalItemDecorator);
        LinearLayoutManager manager3 = new LinearLayoutManager(GameActivity.this,LinearLayoutManager.VERTICAL, false);
        manager.setStackFromEnd(true);
        mSpadesCardRecyclerView.setLayoutManager(manager3);
        mSpadesCardRecyclerView.setAdapter(((CardListAndAdapterModel)mHashMapWithAdapter.get(Suit.SPADES.name())).getmAdapter());
*/
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
    }

    @Override
    public void onGameStarted(String responseMsg) {
    }

    @Override
    public void userSelectedCard(Card card) {
        selectedCardImageView.setImageDrawable(card.getCardFront());
        selectedCardObj = card;
    }
}
