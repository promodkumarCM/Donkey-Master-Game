package com.sparklingapps.cardgamefrog.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sparklingapps.cardgamefrog.GameActivity;
import com.sparklingapps.cardgamefrog.R;
import com.sparklingapps.cardgamefrog.interfaces.OnCardAdapterClick;
import com.sparklingapps.cardgamefrog.model.Card;
import com.sparklingapps.cardgamefrog.utils.CardImageView;

import java.util.ArrayList;
import java.util.List;

public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.ViewHolder> {

    private ArrayList<Card> mCardList;
    private Context mContext;
    private OnCardAdapterClick listener;
    private int lastClickedPosition = -1;

    public MyCardAdapter(Context context, ArrayList<Card> cardList) {

        this.mContext = context;
        this.mCardList = cardList;

    }

    public MyCardAdapter(Context context, ArrayList<Card> cardList, OnCardAdapterClick obj) {

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
        holder.cardFace.setImageDrawable(mCardList.get(position).getCardFront());
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardImageView cardFace;

        public ViewHolder(final View itemView) {
            super(itemView);
            cardFace = (CardImageView) itemView.findViewById(R.id.card_icon);
            cardFace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastClickedPosition = getAdapterPosition();
                    listener.userSelectedCard(mCardList.get(getAdapterPosition()));

                }
            });
        }
    }

}
