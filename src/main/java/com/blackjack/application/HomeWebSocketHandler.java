package com.blackjack.application;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;


/**
 * WebSocket handler for the Home page
 */
@WebSocket
public class HomeWebSocketHandler {

    /**
     *  Executes when a user connects to the WebSocket
     *
     *  Adds user to the WebSocket Session Manager
     *
     * @param user  new user connecting
     */
    @OnWebSocketConnect
    public void onConnect(Session user) {

        WebSocketSessionManager.addUser(user);
        WebSocketSessionManager.updateUser(user);
        System.out.println("New Connection");


    }

    /**
     * Executes when a user disconnects from the Web Socket (namely
     * when they join a Room or exit the site)
     *
     * Removes a user from the WebSocket Session Manager
     *
     * @param user  user to be removed
     * @param statusCode    unused
     * @param reason    unused
     */
    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        WebSocketSessionManager.removeUser(user);
        System.out.println("One Less Connection");
    }

    /**
     * Executes when the client sends a message to the server
     * (currently unused)
     *
     * @param user  client sending a message
     * @param message   content of the message
     */
    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        // Currently Unused
    }

}
