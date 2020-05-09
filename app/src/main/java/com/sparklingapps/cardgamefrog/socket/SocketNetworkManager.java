package com.sparklingapps.cardgamefrog.socket;

import android.util.Log;

import com.sparklingapps.cardgamefrog.interfaces.SocketNetworkInterface;
import com.sparklingapps.cardgamefrog.utils.AppConstants;

import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class SocketNetworkManager {
    private static SocketNetworkManager mInstance;
    private static Socket mSocket;
    private final long CONNECTION_TIMEOUT = 5000;
    private static SocketNetworkInterface mNetworkInterface;
    public static final int RECONNECT_ATTEMPT = 3;
    private final long RECONNECTION_DELAY = 1000;

    private static final String TAG = "SocketNetworkManager";

    private static ArrayList<SocketNetworkInterface> socketNetworkInterfaces = new ArrayList<>();

    public static SocketNetworkManager getInstance() {
        if (mInstance == null) {
            mInstance = new SocketNetworkManager();
        }
        return mInstance;
    }


    /**
     * this will register a socketinterface.
     * last added interface will be called.
     * Don't forget to deregister.... ;)
     * @param socketNetworkInterface
     */
    public static void addSocketNetworkInterface(SocketNetworkInterface socketNetworkInterface) {
        try {
            if (socketNetworkInterface != null) {
                socketNetworkInterfaces.remove(socketNetworkInterface);
                socketNetworkInterfaces.add(socketNetworkInterface);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * this will remove the socketinterface.
     * last added interface will be called.
     * @param socketNetworkInterface
     */
    public static void removeSocketNetworkInterface(SocketNetworkInterface socketNetworkInterface) {
        try {
            if (socketNetworkInterface != null) {
                socketNetworkInterfaces.remove(socketNetworkInterface);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * The purpose of this method to create the socket object
     */
    public void connectToSocket() {
        try {
            //based on the build version the base url is taken and initialize the socket object

            IO.Options options = new IO.Options();
            options.reconnection = true;
            options.reconnectionDelay = RECONNECTION_DELAY;
            options.timeout = CONNECTION_TIMEOUT;
            options.reconnectionAttempts = RECONNECT_ATTEMPT;
            mSocket = IO.socket(AppConstants.baseURL, options);
            makeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The purpose of the method is to return the instance of socket
     *
     * @return current socket if any available
     */
    public Socket getSocket() {
        return mSocket;
    }

    /**
     * The purpose of this method is to connect with the socket
     */

    public void makeConnection() {
        if (mSocket != null) {
            mSocket.on(Socket.EVENT_ERROR, onSocketError);
            mSocket.on(Socket.EVENT_DISCONNECT, onSocketDisconnectListener);
            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onSocketConnectionTimeOut);
            mSocket.on(Socket.EVENT_CONNECT, onSocketConnectListener);
            mSocket.connect();
            if (mSocket.connected()) {
                registerConnectionAttributes();
            }
        }
    }

    /**
     * method used for reconnect to the socket
     */
    public void makeReConnection() {
        if (mSocket != null) {
            mSocket.connect();
        }
    }


    public boolean isConnected() {
        if (mSocket != null) {
            return mSocket.connected();
        }
        return false;
    }

    /**
     * The purpose of this method is to disconnect from the socket interface
     */
    public void disconnectFromSocket() {
        if (mSocket != null && mSocket.connected()) {
            unregisterConnectionAttributes();
            mSocket.disconnect();
        }
        mSocket = null;
        mInstance = null;
    }

    /**
     * the purpose of this method is to add events to the sockets connection and callback register
     */
    public void registerConnectionAttributes() {
        try {
            if (mSocket.connected()) {
                mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectionError);
                mSocket.on(Socket.EVENT_DISCONNECT, onServerDisconnect);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * the purpose od this method to unrgister the socket connection events
     */

    public void unregisterConnectionAttributes() {
        try {
            mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectionError);
            mSocket.off(Socket.EVENT_DISCONNECT, onServerDisconnect);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * The purpose of this method is register a method on server
     * and listen for changes over it
     *
     * @param methodOnServer
     */
    public void registerEventListenerHandler(String methodOnServer) {
        try {
            if (mSocket != null)
                if (mSocket.connected()) {
                    switch (methodOnServer) {
                        case SocketConstants.ON_CONNECTION:
                            mSocket.on(methodOnServer, onConnectedListener);
                            break;
                        case SocketConstants.AFTER_GAME_CREATED:
                            mSocket.on(methodOnServer,onAfterGameCreatedListener);
                            break;

                        case SocketConstants.NEW_MESSAGE:
                            mSocket.on(methodOnServer, onNewMessageReceivedListener);
                            break;

                        case SocketConstants.NOTIFY_PLAYER_JOIN:
                            mSocket.on(methodOnServer,notifyPlayerJoinListener);
                            break;
                        case SocketConstants.ADD_CARD:
                            mSocket.on(methodOnServer,addCardsToHandListener);
                            break;
                        case SocketConstants.STARTED_GAME:
                            mSocket.on(methodOnServer,onGameStartedListener);
                            break;
                        case SocketConstants.ADD_CARD_TABLE:
                            mSocket.on(methodOnServer,addCardToTableListener);
                            break;

                        case SocketConstants.CLEAR_TABLE:
                            mSocket.on(methodOnServer,clearTableListener);
                            break;
                        case SocketConstants.TURN_UPDATE:
                            mSocket.on(methodOnServer,onUpdateTurn);
                            break;
                    }

                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The purpose of this method is to unregister a method from server
     *
     * @param methodOnServer
     * @param handlerName
     */
    public void unRegisterEventListenerHandler(String methodOnServer, Emitter.Listener handlerName) {
        try {
            mSocket.off(methodOnServer, handlerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The purpose of this method is to send the data to the server
     *
     * @param methodOnServer event on server
     * @param jsonRequest    json send to server
     */
    public void sendDataToServer(String methodOnServer, String jsonRequest) {
        try {
            if (mSocket.connected()) {
                mSocket.emit(methodOnServer, jsonRequest);
            } else {
                mNetworkInterface = getSocketNetworkInterface();
                if(mNetworkInterface != null) {
                    mNetworkInterface.networkCallbackReceived();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //...............................CALL BACK LISTENERS STARTS................................................


    /**
     * call back for socket connect
     */

    private Emitter.Listener onSocketConnectListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mNetworkInterface = getSocketNetworkInterface();
            if (mNetworkInterface != null) {
                mNetworkInterface.onConnected(mSocket.connected());
            }

        }
    };



    /*call back for socket disconnection*/

    private Emitter.Listener onSocketDisconnectListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mNetworkInterface = getSocketNetworkInterface();
            if (mNetworkInterface != null)
                mNetworkInterface.onDisconnected(mSocket != null ? !mSocket.connected() : false);
        }
    };


    private Emitter.Listener onSocketError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //Log.d(TAG, "call: socket error ");
            mNetworkInterface = getSocketNetworkInterface();
            if (mNetworkInterface != null)
                mNetworkInterface.onSocketError();
        }
    };

    private Emitter.Listener onSocketConnectionTimeOut = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //Log.d(TAG, "call: connection time out " + args);
            mNetworkInterface = getSocketNetworkInterface();
            if (mNetworkInterface != null)
                mNetworkInterface.onConnectionTimeOut(false);
        }
    };


    /**
     * call back for any type of connection error
     */
    private Emitter.Listener onConnectionError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "onConnectionError :" + args[0]);
            mNetworkInterface = getSocketNetworkInterface();
            if (mNetworkInterface != null)
                mNetworkInterface.networkCallbackReceived();
        }
    };


    /**
     * call back when the server get disconnected
     */
    private Emitter.Listener onServerDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "onServerDisconnect call:" + args[0].toString());
            mNetworkInterface = getSocketNetworkInterface();
            if (mNetworkInterface != null)
                mNetworkInterface.networkCallbackReceived();
        }
    };

    /**
     * call back when server  trigger msg to NEW MSG EVENT
     */
    private Emitter.Listener onNewMessageReceivedListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mNetworkInterface = getSocketNetworkInterface();
            if (mNetworkInterface != null)
                mNetworkInterface.onNewMessageReceived(args[0].toString());
        }
    };


    /**
     * call back when server  trigger msg to Connected EVENT
     */
    private Emitter.Listener onConnectedListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            Log.d(TAG, "<><BASIL TEEST><" + args[0]);
            mNetworkInterface.onConnected(mSocket.connected());

        }
    };


    private Emitter.Listener onAfterGameCreatedListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mNetworkInterface = getSocketNetworkInterface();
            mNetworkInterface.onAfterGameCreated(args[0].toString());
        }
    };

    private Emitter.Listener notifyPlayerJoinListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            mNetworkInterface = getSocketNetworkInterface();
            mNetworkInterface.notifyPlayerJoin(args[0].toString());
        }
    };

    private Emitter.Listener addCardsToHandListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mNetworkInterface = getSocketNetworkInterface();
            mNetworkInterface.addCardToPlayerHand(args[0].toString());
        }
    };

    private Emitter.Listener onGameStartedListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mNetworkInterface = getSocketNetworkInterface();
            mNetworkInterface.onGameStarted(args[0].toString());
        }
    };

    private Emitter.Listener addCardToTableListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mNetworkInterface = getSocketNetworkInterface();
            Log.d(TAG, "call: addCardToTable" +args[0].toString());
            mNetworkInterface.addCardToTable(args[0].toString());
        }
    };



    private Emitter.Listener clearTableListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mNetworkInterface = getSocketNetworkInterface();
            mNetworkInterface.onClearTable(args[0].toString());
        }
    };


    private Emitter.Listener onUpdateTurn = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mNetworkInterface = getSocketNetworkInterface();
            Log.d(TAG, "call: "+args[0]);
            mNetworkInterface.updateTurn((boolean)args[0]);
        }
    };

    private SocketNetworkInterface getSocketNetworkInterface() {


        int size = socketNetworkInterfaces.size() - 1;

       // Log.e("test", "idiot... getSocketNetworkInterface  " +  size ) ;

        if( size > -1) {
            SocketNetworkInterface socketNetworkInterface = socketNetworkInterfaces.get(size);
          //  Log.e("test", "idiot... getSocketNetworkInterface  " +  size + "  currently using " + socketNetworkInterface) ;
            return socketNetworkInterface;
        }
        return null;
    }

}