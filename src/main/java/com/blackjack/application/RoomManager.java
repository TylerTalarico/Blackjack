package com.blackjack.application;

import java.util.concurrent.ConcurrentHashMap;

public class RoomManager {


    private final ConcurrentHashMap<String, Room> roomList = new ConcurrentHashMap<>();



    private static int numRooms = 0;

    public static void createRoom() {
        numRooms++;
    }

    public static void deleteRoom() {
        numRooms--;
    }

    public static String getNumRooms() { return String.valueOf(numRooms); }

}
