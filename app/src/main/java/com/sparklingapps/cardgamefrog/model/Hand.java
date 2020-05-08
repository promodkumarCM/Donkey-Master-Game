package com.sparklingapps.cardgamefrog.model;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.sparklingapps.cardgamefrog.R;
import com.sparklingapps.cardgamefrog.enums.Suit;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

public class Hand {

    private ArrayList<Card> cardsInHand;
    private ArrayList<Card> clubsCardInHand;
    private ArrayList<Card> diamondsCardInHand;
    private ArrayList<Card> heartsCardInHand;
    private ArrayList<Card> spadesCardInHand;

    public Hand() {
        cardsInHand = new ArrayList<>();
        clubsCardInHand = new ArrayList<>();
        diamondsCardInHand = new ArrayList<>();
        heartsCardInHand = new ArrayList<>();
        spadesCardInHand = new ArrayList<>();
    }

    public void buildHand(ArrayList<Card> card, Context context) {
        this.cardsInHand.addAll(card);
        setCardFront(context);
    }

    private void setCardFront(Context context) {

        for (int i = 0; i < cardsInHand.size(); i++) {
            Card card = cardsInHand.get(i);
            String value_suit = card.getValue() + "_" + card.getSuit();
            value_suit = value_suit.toLowerCase().trim();
            int id = context.getResources().getIdentifier(value_suit, "drawable", context.getPackageName());
            card.setCardFront(context.getResources().getDrawable(id, context.getApplicationContext().getTheme()));
            cardsInHand.set(i, card);

            if (card.getSuit().equalsIgnoreCase(Suit.CLUBS.name())) {
                clubsCardInHand.add(card);
            } else if (card.getSuit().equalsIgnoreCase(Suit.DIAMONDS.name())) {
                diamondsCardInHand.add(card);
            } else if (card.getSuit().equalsIgnoreCase(Suit.HEARTS.name())) {
                heartsCardInHand.add(card);
            } else if (card.getSuit().equalsIgnoreCase(Suit.SPADES.name())) {
                spadesCardInHand.add(card);
            }

        }
    }

    public ArrayList<Card> getCardsInHand() {
        return this.cardsInHand;
    }

    public ArrayList<Card> getCardsBySuit(Suit suit) {
        if (suit == Suit.CLUBS) {
            return clubsCardInHand;
        } else if (suit == Suit.DIAMONDS) {
            return diamondsCardInHand;
        } else if (suit == Suit.HEARTS) {
            return heartsCardInHand;
        } else if (suit == Suit.SPADES) {
            return spadesCardInHand;
        } else {
            return null;
        }
    }

    public int getHandSize() {
        return cardsInHand.size();
    }

}
