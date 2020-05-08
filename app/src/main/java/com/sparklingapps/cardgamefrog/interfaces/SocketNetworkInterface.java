package com.sparklingapps.cardgamefrog.interfaces;

public interface SocketNetworkInterface {
    void networkCallbackReceived();

    void onNewMessageReceived(String responseMsg);

    void onConnected(boolean connected);

    void onDisconnected(boolean disConnected);

    void onConnectionTimeOut(boolean status);

    void onSocketError();

    void onAfterGameCreated(String responseMsg);

    void notifyPlayerJoin(String responseMsg);

    void addCardToPlayerHand(String responseMsg);

    void onGameStarted(String responseMsg);

}
