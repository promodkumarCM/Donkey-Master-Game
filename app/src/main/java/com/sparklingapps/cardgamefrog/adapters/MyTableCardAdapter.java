package com.sparklingapps.cardgamefrog.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sparklingapps.cardgamefrog.GameActivity;
import com.sparklingapps.cardgamefrog.R;
import com.sparklingapps.cardgamefrog.enums.Cards;
import com.sparklingapps.cardgamefrog.interfaces.OnCardAdapterClick;
import com.sparklingapps.cardgamefrog.model.Player;
import com.sparklingapps.cardgamefrog.utils.CardImageView;

import java.util.ArrayList;

public class MyTableCardAdapter extends RecyclerView.Adapter<MyTableCardAdapter.ViewHolder> {

    private ArrayList<Integer> mCardList;
    private ArrayList<Player> playerList;
    private Context mContext;
    private OnCardAdapterClick listener;
    private int lastClickedPosition = -1;

    public MyTableCardAdapter(Context context, ArrayList<Integer> cardList) {

        this.mContext = context;
        this.mCardList = cardList;

    }

    public MyTableCardAdapter(Context context, ArrayList<Integer> cardList, ArrayList<Player> playerList) {

        this.mContext = context;
        this.mCardList = cardList;
        this.playerList = playerList;
    }

    public MyTableCardAdapter(Context context, ArrayList<Integer> cardList, OnCardAdapterClick obj) {

        this.mContext = context;
        this.mCardList = cardList;
        this.listener = obj;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.card_table, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            holder.cardFace.setImageResource(Cards.valueOf(mCardList.get(position)).getResource());
            if (playerList != null) {
                holder.tv_playerName.setText(playerList.get(position).getName().toString());
            }
        } catch (Exception exp) {
            Log.d("MyTableCardAdapter", "onBindViewHolder: " + exp);
        }
    }

    @Override
    public int getItemCount() {
         return mCardList.size();
        //return GameActivity.totalPlayerSlot;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardImageView cardFace;
        TextView tv_playerName;

        public ViewHolder(final View itemView) {
            super(itemView);
            cardFace = (CardImageView) itemView.findViewById(R.id.card_icon);
            tv_playerName = itemView.findViewById(R.id.tv_player_name);
            cardFace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastClickedPosition = getAdapterPosition();
                    if (listener != null) {
                        listener.userSelectedCard(mCardList.get(getAdapterPosition()));
                    }

                }
            });
        }
    }

}
