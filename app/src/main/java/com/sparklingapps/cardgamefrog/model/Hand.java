package com.sparklingapps.cardgamefrog.model;

import android.content.Context;

import com.sparklingapps.cardgamefrog.enums.Cards;
import com.sparklingapps.cardgamefrog.enums.Suit;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Integer> temp;
    private ArrayList<Integer> cardsInHand;
    private ArrayList<Integer> clubsCardInHand;
    private ArrayList<Integer> diamondsCardInHand;
    private ArrayList<Integer> heartsCardInHand;
    private ArrayList<Integer> spadesCardInHand;

    public Hand() {
        temp = new ArrayList<>();
        cardsInHand = new ArrayList<>();
        clubsCardInHand = new ArrayList<>();
        diamondsCardInHand = new ArrayList<>();
        heartsCardInHand = new ArrayList<>();
        spadesCardInHand = new ArrayList<>();
    }

    public void clearAllSuits() {

        clubsCardInHand.clear();
        diamondsCardInHand.clear();
        heartsCardInHand.clear();
        spadesCardInHand.clear();
    }

    public void buildHand(ArrayList<Integer> card) {
        this.temp.clear();
        this.temp.addAll(card);
        this.cardsInHand.clear();
        clearAllSuits();
        this.cardsInHand.addAll(temp);
        setCardFront();
    }

    public void addCardToHand(ArrayList<Integer> newArray){

        this.cardsInHand.addAll(newArray);

    }

    public void removeOneCardInHand(Object object) {
        this.cardsInHand.remove(object);
    }

    private void setCardFront() {
        for (int i = 0; i < cardsInHand.size(); i++) {
            Integer card = cardsInHand.get(i);
            if (Cards.valueOf(card).name().substring(0, Cards.valueOf(card).name().indexOf("_")).equalsIgnoreCase(Suit.CLUBS.name())) {
                clubsCardInHand.add(card);
            } else if (Cards.valueOf(card).name().substring(0, Cards.valueOf(card).name().indexOf("_")).equalsIgnoreCase(Suit.DIAMONDS.name())) {
                diamondsCardInHand.add(card);
            } else if (Cards.valueOf(card).name().substring(0, Cards.valueOf(card).name().indexOf("_")).equalsIgnoreCase(Suit.HEARTS.name())) {
                heartsCardInHand.add(card);
            } else if (Cards.valueOf(card).name().substring(0, Cards.valueOf(card).name().indexOf("_")).equalsIgnoreCase(Suit.SPADES.name())) {
                spadesCardInHand.add(card);
            }

        }
    }

    public ArrayList<Integer> getCardsInHand() {
        return this.cardsInHand;
    }

    public ArrayList<Integer> getCardsBySuit(Suit suit) {
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
