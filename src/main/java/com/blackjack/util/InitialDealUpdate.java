package com.blackjack.util;

import com.blackjack.model.Card;
import com.blackjack.model.Player;

public class InitialDealUpdate {
    private final String messageType = "INITIAL_DEAL";
    private final Player player;
    private final Card card;

    public InitialDealUpdate(Player player, Card card) {
        this.player = player;
        this.card = card;
    }
}
