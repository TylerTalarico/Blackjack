package com.blackjack.application;

public class RoomManager {

    private static int numRooms = 0;

    public static void createRoom() {
        numRooms++;
    }

    public static void deleteRoom() {
        numRooms--;
    }

    public static String getNumRooms() {
        return "" + numRooms;
    }

}
