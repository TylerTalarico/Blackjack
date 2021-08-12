package com.blackjack.application;

import com.blackjack.model.Game;
import com.blackjack.model.Player;
import com.blackjack.util.Message;
import com.blackjack.util.RoomData;
import org.eclipse.jetty.websocket.api.Session;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RoomManager {

    private static final ConcurrentHashMap<String, Room> roomList = new ConcurrentHashMap<>();


    public static Room createRoom(String name, Player host, int playerCap, int pointCap) {
        Room room = null;
        if (!roomList.containsKey(name)) {
            room = new Room(host, playerCap, pointCap, name);
            roomList.put(name, room);
        }
        return room;
    }

    public static Room getRoom(String roomName) {
        return roomList.get(roomName);


    }


    public static void addUserToRoom(Session user, Player player, String roomName) {
        Room room = roomList.get(roomName);
        if (room != null) {
            room.addUser(user, player);
        }
    }


    public static void removeUserFromRoom(Session user, Player player, String roomName) {
        Room room = roomList.get(roomName);
        if (room != null) {
            room.removeUser(user, player);
        }
    }


    public static ArrayList<RoomData> getAllRoomData() {
        ArrayList<RoomData> roomData = new ArrayList<>();
        for (Room room: roomList.values())
            roomData.add(room.getRoomData());

        return roomData;


    }


    public static void startRoundInRoom(String roomStarting, Player playerStarting) {
        Room room = roomList.get(roomStarting);
        if (room != null && room.getHost().equals(playerStarting)) {
            room.startRound();
        }
    }

    public static void submitMoveToRoom(String roomForMoveSubmit, Player playerSubmittingMove, Game.ActionType moveType) {
        Room room = roomList.get(roomForMoveSubmit);
        if (room != null) {
            room.performAction(moveType, playerSubmittingMove);
        }
    }
}
