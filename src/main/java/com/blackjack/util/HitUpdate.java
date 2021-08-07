package com.blackjack.util;

import com.blackjack.model.Card;
import com.blackjack.model.Player;

public class HitUpdate extends GameUpdate {

    private static final String messageType = "HIT";
    private final Player activePlayer;
    private final Player newActivePlayer;
    private final Card cardDrawn;
    private final boolean bust;

    public HitUpdate(Player activePlayer, Player newActivePlayer, Card cardDrawn, boolean bust) {
        super(messageType);
        this.activePlayer = activePlayer;
        this.newActivePlayer = newActivePlayer;
        this.cardDrawn = cardDrawn;
        this.bust = bust;
    }
}
