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
    private final String START_GAME_REQUEST = "startGame";
    public static final String PLAYER_CLOSE_MSG = "playerClose";


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

                String[] args1 = msg.contents().split(" ");
                String roomJoining = args1[0];
                String playerNameJoining = args1[1];
                Player playerJoining = PlayerServices.getPlayer(playerNameJoining);

                RoomManager.addUserToRoom(user, playerJoining, roomJoining);
                System.out.println("Room Request Received");
                break;


            case PLAYER_CLOSE_MSG:

                String[] args2 = msg.contents().split(" ");
                String roomLeaving = args2[0];
                String playerNameLeaving = args2[1];
                Player playerLeaving = PlayerServices.getPlayer(playerNameLeaving);

                RoomManager.removeUserFromRoom(user, playerLeaving, roomLeaving);

            case START_GAME_REQUEST:

                String[] args3 = msg.contents().split(" ");
                String roomStarting = args3[0];
                String playerNameStarting = args3[1];

                Player playerStarting = PlayerServices.getPlayer(playerNameStarting);
                if (playerStarting != null)
                    RoomManager.startGameInRoom(roomStarting, playerStarting);
            default:
                break;

        }
    }


}
