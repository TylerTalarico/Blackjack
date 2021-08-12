package com.blackjack.util.GameUpdate;

public class ClearHandUpdate extends GameUpdate{

    private static String messageType = "CLEAR_HAND";
    public ClearHandUpdate() {
        super(messageType);
    }
}
