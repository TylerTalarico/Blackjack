package com.blackjack.application;

import com.blackjack.model.Game;
import com.blackjack.model.Player;
import com.blackjack.util.LobbyUpdate.RemoveRoomUpdate;
import com.blackjack.util.RoomData;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;

import java.util.concurrent.ConcurrentHashMap;

public class RoomManager {

    // thread safe Hash Map for storing user WebSocket sessions
    private static final ConcurrentHashMap<String, Room> roomList = new ConcurrentHashMap<>();


    /**
     * Create a room with the given arguments
     *
     * @param name
     *      name of the room
     * @param host
     *      Player object of the host user
     * @param playerCap
     *      max number of players in the room
     * @param pointCap
     *      number of points to win the game
     * @return
     *      the corresponding room object
     */
    public static Room createRoom(String name, Player host, int playerCap, int pointCap) {
        Room room = null;
        if (!roomList.containsKey(name)) {
            room = new Room(host, playerCap, pointCap, name);
            roomList.put(name, room);
        }
        return room;
    }

    /**
     * @param roomName
     *      name of the desired room
     * @return
     *      the desired room object
     */
    public static Room getRoom(String roomName) { return roomList.get(roomName); }


    /**
     * Adds a user to the specified room
     *
     * @param user
     *      user's WebSocket session
     * @param player
     *      user's Player object
     * @param roomName
     *      name of the room
     */
    public static void addUserToRoom(Session user, Player player, String roomName) {
        Room room = roomList.get(roomName);
        if (room != null) {
            room.addUser(user, player);
        }
    }


    /**
     * Remove a user from a room
     *
     * @param user
     *      user's WebSocket session
     * @param player
     *      user's Player object
     * @param roomName
     *      name of the room
     */
    public static void removeUserFromRoom(Session user, Player player, String roomName) {
        Room room = roomList.get(roomName);
        if (room != null) {
            room.removeUser(user, player);
        }
    }


    /**
     * Get a list of RoomData objects to create UI room elements
     *
     * @return
     *      list of all RoomData objects
     */
    public static ArrayList<RoomData> getAllRoomData() {
        ArrayList<RoomData> roomData = new ArrayList<>();
        for (Room room: roomList.values())
            roomData.add(room.getRoomData());

        return roomData;
    }


    /**
     * Start a round in a Room
     *
     * @param roomStarting
     *      name of the Room that is starting its game
     * @param playerStarting
     *      Player starting the game
     */
    public static void startRoundInRoom(String roomStarting, Player playerStarting) {
        Room room = roomList.get(roomStarting);

        // Game will not start if the host is not starting it
        if (room != null && room.getHost().equals(playerStarting)) {
            room.startRound();
        }
    }

    /**
     * Submit a move to a room
     *
     * @param roomForMoveSubmit
     *      name of the room a move is being made in
     * @param playerSubmittingMove
     *      player submitting the move
     * @param moveType
     *      type of move being made
     */
    public static void submitMoveToRoom(String roomForMoveSubmit, Player playerSubmittingMove, Game.ActionType moveType) {
        Room room = roomList.get(roomForMoveSubmit);
        if (room != null) {
            room.performAction(moveType, playerSubmittingMove);
        }
    }

    /**
     * Removes a room from the list of all rooms
     * This is really only done if the room is empty
     *
     * @param roomName
     *      name of the room being removed
     */
    public static void removeRoom(String roomName) {
        roomList.remove(roomName);
        WebSocketSessionManager.updateAllClients(new RemoveRoomUpdate(roomName));
    }
}
