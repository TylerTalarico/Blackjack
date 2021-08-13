package com.blackjack.util.GameUpdate;

import com.blackjack.model.Player;

public class StandUpdate extends ActivePlayerShiftUpdate{

    private static final String messageType = "STAND";
    private final Player activePlayer;

    public StandUpdate(Player activePlayer, Player newActivePlayer) {
        super(messageType, newActivePlayer);
        this.activePlayer = activePlayer;
    }
}
