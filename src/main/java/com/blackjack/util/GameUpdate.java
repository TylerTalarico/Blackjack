package com.blackjack.util;

import com.blackjack.model.Card;

public abstract class GameUpdate {


    protected String messageType;

    public GameUpdate(String messageType) {
        this.messageType = messageType;
    }
}
