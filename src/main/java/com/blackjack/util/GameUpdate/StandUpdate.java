package com.blackjack.util.GameUpdate;

import com.blackjack.model.Player;

public class StandUpdate extends GameUpdate{

    private static final String messageType = "STAND";
    private final Player activePlayer;
    private final Player newActivePlayer;

    public StandUpdate(Player activePlayer, Player newActivePlayer) {
        super(messageType);
        this.activePlayer = activePlayer;
        this.newActivePlayer = newActivePlayer;
    }
}
