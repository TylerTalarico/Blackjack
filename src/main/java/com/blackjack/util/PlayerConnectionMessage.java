package com.blackjack.util;

import com.blackjack.model.Player;

public class PlayerConnectionMessage {

    public enum ConnectionType {
        CONNECT,
        DISCONNECT

    }

    private final ConnectionType messageType;
    private final Player playerLeaving;
    private final Player newActivePlayer;

    public PlayerConnectionMessage(ConnectionType type, Player player, Player newActivePlayer) {
        this.messageType = type;
        this.playerLeaving = player;
        this.newActivePlayer = newActivePlayer;
    }
}
