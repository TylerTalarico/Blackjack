package com.blackjack.util.LobbyUpdate;

public class RoomNumberPlayersUpdate extends LobbyUpdate{

    private static final String messageType = "ROOM_NUM_PLAYERS_UPDATE";
    private final String roomName;
    private final int numPlayers;

    public RoomNumberPlayersUpdate(String roomName, int numPlayers) {
        super(messageType);
        this.roomName = roomName;
        this.numPlayers = numPlayers;
    }
}
