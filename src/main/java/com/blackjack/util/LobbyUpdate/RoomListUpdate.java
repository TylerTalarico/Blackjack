package com.blackjack.util.LobbyUpdate;

import com.blackjack.util.RoomData;

import java.util.ArrayList;

public class RoomListUpdate extends LobbyUpdate{

    private static final String messageType = "ROOM_LIST";
    private final ArrayList<RoomData> roomDataList;

    public RoomListUpdate(ArrayList<RoomData> roomDataList) {
        super(messageType);
        this.roomDataList = roomDataList;
    }
}
