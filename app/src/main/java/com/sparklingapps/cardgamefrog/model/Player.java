package com.sparklingapps.cardgamefrog.model;


import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player {

    @SerializedName("position")
    @Expose
    private Integer position;
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
    private ArrayList<Integer> card = null;
    @SerializedName("isMyTurn")
    @Expose
    private Boolean isMyTurn;

    @SerializedName("totalPlayers")
    @Expose
    private Integer totalPlayers;

    /**
     * No args constructor for use in serialization
     *
     */
    public Player() {
    }

    /**
     *
     * @param name
     * @param position
     * @param id
     * @param type
     * @param isMyTurn
     * @param roomId
     * @param card
     */
    public Player(Integer position, String id, Integer roomId, String name, String type, ArrayList<Integer> card, Boolean isMyTurn,Integer totalPlayers) {
        super();
        this.position = position;
        this.id = id;
        this.roomId = roomId;
        this.name = name;
        this.type = type;
        this.card = card;
        this.isMyTurn = isMyTurn;
        this.totalPlayers = totalPlayers;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
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

    public ArrayList<Integer> getCard() {
        return card;
    }

    public void setCard(ArrayList<Integer> card) {
        this.card = card;
    }

    public Boolean getIsMyTurn() {
        return isMyTurn;
    }

    public void setIsMyTurn(Boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
    }

    public Integer getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(Integer totalPlayers) {
        this.totalPlayers = totalPlayers;
    }
}