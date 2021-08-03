package com.blackjack.util;

import com.blackjack.model.Player;

import java.util.Collection;

public class PlayerListMessage {

    private final String messageType;
    private final Collection<Player> players;

    public PlayerListMessage(String msgType, Collection<Player> players) {
        this.messageType = msgType;
        this.players = players;
    }

}
