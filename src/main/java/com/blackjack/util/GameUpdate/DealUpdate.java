package com.blackjack.util.GameUpdate;

import com.blackjack.model.Card;
import com.blackjack.model.Player;

public class DealUpdate extends GameUpdate{
    private static final String messageType = "INITIAL_DEAL";
    private final Player player;
    private final Card card;

    public DealUpdate(Player player, Card card) {
        super(messageType);
        this.player = player;
        this.card = card;
    }
}
