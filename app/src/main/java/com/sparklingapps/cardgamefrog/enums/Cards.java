package com.sparklingapps.cardgamefrog.enums;

import com.sparklingapps.cardgamefrog.R;

import java.util.HashMap;
import java.util.Map;

public enum Cards {

    DIAMONDS_TWO(0, R.drawable.two_diamonds),
    DIAMONDS_THREE(1, R.drawable.three_diamonds),
    DIAMONDS_FOUR(2, R.drawable.four_diamonds),
    DIAMONDS_FIVE(3, R.drawable.five_diamonds),
    DIAMONDS_SIX(4, R.drawable.six_diamonds),
    DIAMONDS_SEVEN(5, R.drawable.seven_diamonds),
    DIAMONDS_EIGHT(6, R.drawable.eight_diamonds),
    DIAMONDS_NINE(7, R.drawable.nine_diamonds),
    DIAMONDS_TEN(8, R.drawable.ten_diamonds),
    DIAMONDS_JACK(9, R.drawable.jack_diamonds),
    DIAMONDS_QUEEN(10, R.drawable.queen_diamonds),
    DIAMONDS_KING(11, R.drawable.king_diamonds),
    DIAMONDS_ACE(12, R.drawable.ace_diamonds),

    CLUBS_TWO(13, R.drawable.two_clubs),
    CLUBS_THREE(14, R.drawable.three_clubs),
    CLUBS_FOUR(15, R.drawable.four_clubs),
    CLUBS_FIVE(16, R.drawable.five_clubs),
    CLUBS_SIX(17, R.drawable.six_clubs),
    CLUBS_SEVEN(18, R.drawable.seven_clubs),
    CLUBS_EIGHT(19, R.drawable.eight_clubs),
    CLUBS_NINE(20, R.drawable.nine_clubs),
    CLUBS_TEN(21, R.drawable.ten_clubs),
    CLUBS_JACK(22, R.drawable.jack_clubs),
    CLUBS_QUEEN(23, R.drawable.queen_clubs),
    CLUBS_KING(24, R.drawable.king_clubs),
    CLUBS_ACE(25, R.drawable.ace_clubs),

    HEARTS_TWO(26, R.drawable.two_hearts),
    HEARTS_THREE(27, R.drawable.three_hearts),
    HEARTS_FOUR(28, R.drawable.four_hearts),
    HEARTS_FIVE(29, R.drawable.five_hearts),
    HEARTS_SIX(30, R.drawable.six_hearts),
    HEARTS_SEVEN(31, R.drawable.seven_hearts),
    HEARTS_EIGHT(32, R.drawable.eight_hearts),
    HEARTS_NINE(33, R.drawable.nine_hearts),
    HEARTS_TEN(34, R.drawable.ten_hearts),
    HEARTS_JACK(35, R.drawable.jack_hearts),
    HEARTS_QUEEN(36, R.drawable.queen_hearts),
    HEARTS_KING(37, R.drawable.king_hearts),
    HEARTS_ACE(38, R.drawable.ace_hearts),


    SPADES_TWO(39, R.drawable.two_spades),
    SPADES_THREE(40, R.drawable.three_spades),
    SPADES_FOUR(41, R.drawable.four_spades),
    SPADES_FIVE(42, R.drawable.five_spades),
    SPADES_SIX(43, R.drawable.six_spades),
    SPADES_SEVEN(44, R.drawable.seven_spades),
    SPADES_EIGHT(45, R.drawable.eight_spades),
    SPADES_NINE(46, R.drawable.nine_spades),
    SPADES_TEN(47, R.drawable.ten_spades),
    SPADES_JACK(48, R.drawable.jack_spades),
    SPADES_QUEEN(49, R.drawable.queen_spades),
    SPADES_KING(50, R.drawable.king_spades),
    SPADES_ACE(51, R.drawable.ace_spades);


    private static Map<Integer, Cards> map = new HashMap<>();

    static {
        for (Cards card : Cards.values()) {
            map.put(card.id, card);
        }
    }

    int id;
    int resource;

    Cards(int id, int resource) {
        this.id = id;
        this.resource = resource;
    }

    public int getId() {
        return id;
    }

    public int getResource() {
        return resource;
    }

    public static Cards valueOf(int cardId) {
        return (Cards) map.get(cardId);
    }

}