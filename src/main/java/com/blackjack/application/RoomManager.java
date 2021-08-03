package com.blackjack.application;

import com.blackjack.model.Player;
import com.blackjack.util.Message;
import com.blackjack.util.PlayerListMessage;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RoomManager {

    private final static String PLAYER_CONNECTION_MSG = "playerConnection";
    private static final ConcurrentHashMap<String, Room> roomList = new ConcurrentHashMap<>();


    public static void createRoom(String name, Player host, int playerCap, int pointCap) {
        if (!roomList.containsKey(name)) {
            roomList.put(name, new Room(host, playerCap, pointCap, name));
        }
    }

    public static Room getRoom(String roomName) {
        return roomList.get(roomName);


    }

    public static void addPlayerToRoom(Player player, String roomName) {
        Room room = roomList.get(roomName);
        if (room != null) {
            room.addPlayer(player);
        }

    }

    public static void addUserToRoom(Session user, String roomName) {
        Room room = roomList.get(roomName);
        if (room != null) {
            room.addUser(user);
            System.out.println("Player added to room " + roomName);
        }
    }

    public static void removePlayerFromRoom(Player player, String roomName) {
        Room room = roomList.get(roomName);
        if (room != null) {
            room.removePlayer(player);
        }
    }

    public static void removeUserFromRoom(Session user, String roomName) {
        Room room = roomList.get(roomName);
        if (room != null) {
            room.removeUser(user);
        }
    }

    public static void updateRoomGameState(String roomName, Message msg) {
        Room room = roomList.get(roomName);
        room.updateGameState(msg);
    }


    public static Set<String> getRoomNames() {
        return roomList.keySet();

    }


}
