package com.blackjack.util.LobbyUpdate;

public class RemoveRoomUpdate extends LobbyUpdate{

    private final static String messageType = "REMOVE_ROOM";
    private final String roomName;

    public RemoveRoomUpdate(String roomName) {
        super(messageType);
        this.roomName = roomName;
    }
}
