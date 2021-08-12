package com.blackjack.util.GameUpdate;

import com.blackjack.model.Card;

public abstract class GameUpdate {


    protected String messageType;

    public GameUpdate(String messageType) {
        this.messageType = messageType;
    }
}
