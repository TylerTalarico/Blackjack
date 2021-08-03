package com.blackjack.application;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class HomeWebSocketHandler {
    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        WebSocketSessionManager.addUser(user);
        WebSocketSessionManager.updateUser(user);
        System.out.println("New Connection");

    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        WebSocketSessionManager.removeUser(user);
        System.out.println("One Less Connection");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        // TODO: Check for room creation and update all clients
    }

}
