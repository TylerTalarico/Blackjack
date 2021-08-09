package com.blackjack.util;

import com.blackjack.model.Card;
import com.blackjack.model.Player;

public class HitUpdate extends GameUpdate {

    private static final String messageType = "HIT";
    private final Player activePlayer;
    private final Player newActivePlayer;
    private final Card card;
    private final boolean bust;

    public HitUpdate(Player activePlayer, Player newActivePlayer, Card card, boolean bust) {
        super(messageType);
        this.activePlayer = activePlayer;
        this.newActivePlayer = newActivePlayer;
        this.card = card;
        this.bust = bust;
    }
}
