package com.sparklingapps.cardgamefrog.model;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cardsInHand;

    public void buildHand(Card card) {
        this.cardsInHand.add(card);
    }

    public ArrayList<Card> getCardsInHand() {
        return this.cardsInHand;
    }

    public int getHandSize() {
        return cardsInHand.size();
    }

}
