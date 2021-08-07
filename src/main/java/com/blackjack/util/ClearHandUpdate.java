package com.blackjack.util;

public class ClearHandUpdate extends GameUpdate{

    private static String messageType = "CLEAR_HAND";
    public ClearHandUpdate() {
        super(messageType);
    }
}
