package com.blackjack.util.GameUpdate;

import com.blackjack.model.Player;
import com.blackjack.util.GameUpdate.GameUpdate;

public class PlayerDisconnectUpdate extends ActivePlayerShiftUpdate {

    private final static String messageType = "DISCONNECT";
    private final Player playerLeaving;

    public PlayerDisconnectUpdate(Player player, Player newActivePlayer) {
        super(messageType, newActivePlayer);
        this.playerLeaving = player;
    }
}
