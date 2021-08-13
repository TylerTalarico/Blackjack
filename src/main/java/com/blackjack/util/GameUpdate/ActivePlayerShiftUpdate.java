package com.blackjack.util.GameUpdate;

import com.blackjack.model.Player;

public abstract class ActivePlayerShiftUpdate extends GameUpdate{

    private final Player newActivePlayer;

    public ActivePlayerShiftUpdate(String messageType, Player newActivePlayer) {
        super(messageType);
        this.newActivePlayer = newActivePlayer;
    }
}
