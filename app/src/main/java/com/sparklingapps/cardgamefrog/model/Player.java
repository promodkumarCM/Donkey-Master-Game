package com.sparklingapps.cardgamefrog.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player {

    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("roomId")
    @Expose
    private Integer roomId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("card")
    @Expose
    private ArrayList<Card> card = null;
    @SerializedName("isMyTurn")
    @Expose
    private Boolean isMyTurn;


    public Player() {
        super();
    }


    public Player(String id, Integer roomId, String name, String type, ArrayList<Card> card, Boolean isMyTurn) {
        super();
        this.id = id;
        this.roomId = roomId;
        this.name = name;
        this.type = type;
        this.card = card;
        this.isMyTurn = isMyTurn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Card> getCard() {
        return card;
    }

    public void setCard(ArrayList<Card> card) {
        this.card = card;
    }

    public Boolean getIsMyTurn() {
        return isMyTurn;
    }

    public void setIsMyTurn(Boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    @NonNull
    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, Player.class);
    }
}