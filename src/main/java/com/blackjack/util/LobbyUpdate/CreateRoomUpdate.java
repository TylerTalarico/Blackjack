package com.blackjack.util.LobbyUpdate;

import com.blackjack.application.Room;
import com.blackjack.util.RoomData;

public class CreateRoomUpdate extends LobbyUpdate{

    private static final String messageType = "CREATE_ROOM";

    private final RoomData roomData;

    public CreateRoomUpdate(RoomData roomData) {
        super(messageType);
        this.roomData = roomData;
    }
}
