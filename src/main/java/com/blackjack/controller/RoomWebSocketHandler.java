package com.blackjack.controller;

import com.blackjack.application.RoomManager;
import com.blackjack.application.SessionManager;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;

@WebSocket
public class RoomWebSocketHandler {


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


