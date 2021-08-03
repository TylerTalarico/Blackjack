package com.blackjack.application;

import com.blackjack.controller.PlayerServices;
import com.blackjack.model.Player;
import com.blackjack.util.Message;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class RoomWebSocketHandler {

    private final String ROOM_NAME_REQUEST = "getRoomName";
    public static final String PLAYER_CLOSE_MSG = "playerClose";
    public static final String PLAYER_CONNECTION_MSG = "playerConnection";


    private final Gson gson = new Gson();

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        System.out.println("Room Joined");

    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Room Exited");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        // TODO: Check message type and move User to/from room,
        //       Check message for move type

        System.out.println(message);
        Message msg = gson.fromJson(message, Message.class);
        String messageType = msg.messageType();

        switch (messageType) {

            case ROOM_NAME_REQUEST:

                String roomName = msg.contents();
                RoomManager.addUserToRoom(user, roomName);
                System.out.println("Room Request Received");
                break;


            case PLAYER_CLOSE_MSG:

                String[] arguments = msg.contents().split(" ");
                String roomLeaving = arguments[0];
                String playerName = arguments[1];
                Player playerLeaving = PlayerServices.getPlayer(playerName);

                System.out.println(message);

                RoomManager.removePlayerFromRoom(playerLeaving, roomLeaving);
                RoomManager.removeUserFromRoom(user, roomLeaving);
            default:
                break;

        }
    }


}
