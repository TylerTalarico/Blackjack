package com.blackjack.util.LobbyUpdate;


public abstract class LobbyUpdate {

    private final String messageType;

    public LobbyUpdate(String mt) {
        this.messageType = mt;
    }
}
