package com.blackjack.util;

import com.blackjack.model.Card;

public class GameUpdate {

    private final String activePlayerName;
    private final Card cardDrawn;
    private final boolean bust;
    private final String newActivePlayerName;

    public GameUpdate(String activePlayerName, Card cardDrawn, boolean bust, String newActivePlayerName) {
        this.activePlayerName = activePlayerName;
        this.cardDrawn = cardDrawn;
        this.bust = bust;
        this.newActivePlayerName = newActivePlayerName;
    }
}
