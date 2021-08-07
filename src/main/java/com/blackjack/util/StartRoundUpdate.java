package com.blackjack.util;


import com.blackjack.model.Player;

public class StartRoundUpdate extends GameUpdate {

    private final static String messageType = "START_ROUND";
    private final Player activePlayer;
    public StartRoundUpdate(Player activePlayer) {
        super(messageType);
        this.activePlayer = activePlayer;
    }
}
