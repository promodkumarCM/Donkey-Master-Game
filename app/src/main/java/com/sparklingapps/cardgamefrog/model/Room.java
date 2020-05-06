package com.sparklingapps.cardgamefrog.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class Room {

    @SerializedName("roomId")
    @Expose
    private Integer roomId;
    @SerializedName("socketId")
    @Expose
    private String socketId;
    @SerializedName("roomName")
    @Expose
    private String roomName;
    @SerializedName("roomTotalCount")
    @Expose
    private Integer roomTotalCount;

    @SerializedName("playerObj")
    @Expose
    private Player playerObj;



    /**
     * No args constructor for use in serialization
     *
     */
    public Room() {
    }

    /**
     *
     * @param socketId
     * @param roomTotalCount
     * @param roomId
     * @param roomName

     */
    public Room(Integer roomId, String socketId, String roomName, Integer roomTotalCount,Player playerObj) {
        super();
        this.roomId = roomId;
        this.socketId = socketId;
        this.roomName = roomName;
        this.roomTotalCount = roomTotalCount;
        this.playerObj = playerObj;

    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomTotalCount() {
        return roomTotalCount;
    }

    public void setRoomTotalCount(Integer roomTotalCount) {
        this.roomTotalCount = roomTotalCount;
    }

    public Player getPlayerObj() {
        return playerObj;
    }

    public void setPlayerObj(Player playerObj) {
        this.playerObj = playerObj;
    }
}