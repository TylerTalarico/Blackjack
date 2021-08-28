package com.blackjack.application;

import com.blackjack.model.Game;
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

    private final String ROOM_NAME_REQUEST = "GET_ROOM_NAME";
    private final String START_GAME_REQUEST = "START_GAME";
    public static final String PLAYER_CLOSE_MSG = "PLAYER_CLOSE";
    private final String SUBMIT_MOVE_REQUEST = "SUBMIT_MOVE";


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
        String[] messageContents = parseMessageContents(msg.contents());

        /*
        Handles the data based on what kind of message it is
         */

        switch (messageType) {

            case ROOM_NAME_REQUEST:

                String roomJoining = messageContents[0];
                String playerNameJoining = messageContents[1];
                Player playerJoining = PlayerServices.getPlayer(playerNameJoining);

                RoomManager.addUserToRoom(user, playerJoining, roomJoining);
                System.out.println("Room Request Received");
                break;



            case PLAYER_CLOSE_MSG:

                String roomLeaving = messageContents[0];
                String playerNameLeaving = messageContents[1];
                Player playerLeaving = PlayerServices.getPlayer(playerNameLeaving);

                RoomManager.removeUserFromRoom(user, playerLeaving, roomLeaving);
                break;

            case START_GAME_REQUEST:

                String roomStarting = messageContents[0];
                String playerNameStarting = messageContents[1];

                Player playerStarting = PlayerServices.getPlayer(playerNameStarting);
                if (playerStarting != null)
                    RoomManager.startRoundInRoom(roomStarting, playerStarting);
                break;

            case SUBMIT_MOVE_REQUEST:

                String roomForMoveSubmit = messageContents[0];
                String playerNameSubmittingMove = messageContents[1];
                Game.ActionType moveType = parseAction(messageContents[2]);



                Player playerSubmittingMove = PlayerServices.getPlayer(playerNameSubmittingMove);
                RoomManager.submitMoveToRoom(roomForMoveSubmit, playerSubmittingMove, moveType);
                break;

            default:
                break;

        }
    }

    /**
     * Parses data from a message from a client
     * @param
     *      contents  message contents
     * @return
     *      the contents in an array
     */
    private String[] parseMessageContents (String contents) {
        // The message contents are separated using this regex
        return contents.split("<>");
    }

    /**
     * Turns String representing an game action {@link Game.ActionType}
     * into an ActionType
     *
     * @param action
     *      action taken
     * @return
     *      ActionType object
     */
    private Game.ActionType parseAction(String action) {
        switch(action) {
            case "HIT":
                return Game.ActionType.HIT;

            default:
                return Game.ActionType.STAND;
        }
    }


}
