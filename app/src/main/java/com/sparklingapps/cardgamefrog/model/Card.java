package com.sparklingapps.cardgamefrog.model;

import android.graphics.drawable.Drawable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card {

    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("Suit")
    @Expose
    private String suit;

    private Drawable cardFront;

    /**
     * No args constructor for use in serialization
     *
     */
    public Card() {
    }

    /**
     *
     * @param suit
     * @param value
     */
    public Card(String value, String suit) {
        super();
        this.value = value;
        this.suit = suit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public Drawable getCardFront() {
        return cardFront;
    }

    public void setCardFront(Drawable cardFront) {
        this.cardFront = cardFront;
    }

}
