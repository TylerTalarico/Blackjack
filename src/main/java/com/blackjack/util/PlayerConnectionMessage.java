package com.blackjack.util;

import com.blackjack.model.Player;

public class PlayerConnectionMessage {

    public enum ConnectionType {
        CONNECT,
        DISCONNECT

    }

    private final ConnectionType messageType;
    private final Player player;

    public PlayerConnectionMessage(ConnectionType type, Player player) {
        this.messageType = type;
        this.player = player;
    }
}
