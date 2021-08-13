package com.blackjack.util.GameUpdate;


import com.blackjack.model.Player;

public class StartRoundUpdate extends ActivePlayerShiftUpdate {

    private final static String messageType = "START_ROUND";

    public StartRoundUpdate(Player newActivePlayer) {
        super(messageType, newActivePlayer);
    }
}
