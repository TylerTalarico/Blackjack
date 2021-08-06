package com.blackjack.util;

import com.blackjack.application.RoomWebSocketHandler;
import com.blackjack.model.Player;

import java.util.ArrayList;
import java.util.Collection;

public class PlayerListMessage {

    private String messageType = "playerList";
    private Collection<Player> players;

    public PlayerListMessage(Collection<Player> players) {
        this.players = players;
    }
}
