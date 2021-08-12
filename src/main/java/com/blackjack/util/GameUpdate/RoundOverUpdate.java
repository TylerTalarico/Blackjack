package com.blackjack.util.GameUpdate;

import com.blackjack.model.Player;

public class RoundOverUpdate extends GameUpdate{

    private static final String messageType = "ROUND_OVER";
    private final Player winner;

    public RoundOverUpdate(Player winner) {
        super(messageType);
        this.winner = winner;
    }
}
