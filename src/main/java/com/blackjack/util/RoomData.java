package com.blackjack.util;

public class RoomData {

    private final String roomName;
    private final int playerCap;
    private final int currentNumPlayers;
    private final int pointCap;

    public RoomData(String roomName, int playerCap, int currentNumPlayers, int pointCap) {
        this.roomName = roomName;

        this.playerCap = playerCap;
        this.currentNumPlayers = currentNumPlayers;
        this.pointCap = pointCap;
    }
}
