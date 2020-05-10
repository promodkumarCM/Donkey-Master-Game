package com.sparklingapps.cardgamefrog.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sparklingapps.cardgamefrog.R;
import com.sparklingapps.cardgamefrog.enums.Cards;
import com.sparklingapps.cardgamefrog.interfaces.OnCardAdapterClick;
import com.sparklingapps.cardgamefrog.utils.CardImageView;

import java.util.ArrayList;

public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.ViewHolder> {

    private ArrayList<Integer> mCardList;
    private Context mContext;
    private OnCardAdapterClick listener;
    private int lastClickedPosition = -1;

    private boolean isClickable = false;

    public MyCardAdapter(Context context, ArrayList<Integer> cardList) {

        this.mContext = context;
        this.mCardList = cardList;

    }

    public void setIsClickable(boolean clickable){
        this.isClickable = clickable;
    }

    public MyCardAdapter(Context context, ArrayList<Integer> cardList, OnCardAdapterClick obj) {

        this.mContext = context;
        this.mCardList = cardList;
        this.listener = obj;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (lastClickedPosition == position) {
           /* if (holder.cardFace.isSelected()) {
                holder.cardFace.unSelect();
            } else {
                holder.cardFace.select();
            }*/
        }
        holder.cardFace.setImageResource(Cards.valueOf(mCardList.get(position)).getResource());

        if (isClickable) {
            holder.overLay.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardImageView cardFace;
        RelativeLayout overLay;

        public ViewHolder(final View itemView) {
            super(itemView);
            cardFace = (CardImageView) itemView.findViewById(R.id.card_icon);
            overLay = itemView.findViewById(R.id.rl_overlay);
            cardFace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isClickable) {
                        lastClickedPosition = getAdapterPosition();
                        if (listener != null) {
                            listener.userSelectedCard(mCardList.get(getAdapterPosition()));
                        }
                    }
                }
            });
        }
    }

}
