package com.blackjack.application;


import com.blackjack.model.Player;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.ArrayList;

import static spark.Spark.webSocket;

public class Room {


    @WebSocket
    private class RoomWSHandler {
        @OnWebSocketConnect
        public void onConnect(Session user) throws Exception {
            RoomManager.createRoom();
            SessionManager.addSession(user);
            SessionManager.updateClients();
            System.out.print("New Connection");

        }

        @OnWebSocketClose
        public void onClose(Session user, int statusCode, String reason) {
            RoomManager.deleteRoom();
            SessionManager.deleteSession(user);
            SessionManager.updateClients();
            System.out.print("One Less Connection");
        }

        @OnWebSocketMessage
        public void onMessage(Session user, String message) {
            SessionManager.updateClients();
        }
    }

    private final ArrayList<Player> playerList = new ArrayList<>();
    private final int playerCap;
    private final String roomName;

    public Room(Player host, int playerCap, String roomName) {
        this.playerCap = playerCap;
        this.roomName = roomName;
        playerList.add(host);
        webSocket("/room" + roomName, RoomWSHandler.class);
    }




}
