package com.blackjack.util.GameUpdate;

import com.blackjack.model.Player;

public class GameOverUpdate extends GameUpdate{

    private static final String messageType = "GAME_OVER";
    private final Player winner;

    public GameOverUpdate(Player winner) {
        super(messageType);
        this.winner = winner;
    }
}
