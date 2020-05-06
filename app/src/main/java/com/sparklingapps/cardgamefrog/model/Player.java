package com.sparklingapps.cardgamefrog.model;

public class Player {
    private String id;
    private String roomId;
    private String name;
    private String type;
    private boolean isMyTurn;
    private Card cards[];

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getRoomId() { return roomId; }

    public void setRoomId(String roomId) { this.roomId = roomId; }

    public Card[] getHand() { return cards; }

    public void setHand(Card[] cards) { this.cards = cards; }
}
