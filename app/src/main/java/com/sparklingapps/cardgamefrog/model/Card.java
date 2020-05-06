package com.sparklingapps.cardgamefrog.model;

import com.sparklingapps.cardgamefrog.enums.Suit;
import com.sparklingapps.cardgamefrog.enums.Values;

public class Card {

    private Suit suit;
    private Values values;

    public Card() {
    }

    public Card(Suit suit, Values values) {
        this.values = values;
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Values getValues() {
        return values;
    }

    public void setValues(Values values) {
        this.values = values;
    }
}
