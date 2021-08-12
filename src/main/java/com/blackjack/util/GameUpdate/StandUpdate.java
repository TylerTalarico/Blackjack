package com.blackjack.util.GameUpdate;

import com.blackjack.model.Player;

public class StandUpdate extends GameUpdate{

    private static final String messageType = "STAND";
    private final Player newActivePlayer;

    public StandUpdate(Player newActivePlayer) {
        super(messageType);
        this.newActivePlayer = newActivePlayer;
    }
}
