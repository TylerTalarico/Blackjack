package com.blackjack.util.GameUpdate;

import com.blackjack.model.Card;
import com.blackjack.model.Player;

public class HitUpdate extends ActivePlayerShiftUpdate {

    private static final String messageType = "HIT";
    private final Player activePlayer;
    private final Card card;
    private final boolean bust;

    public HitUpdate(Player activePlayer, Player newActivePlayer, Card card, boolean bust) {
        super(messageType, newActivePlayer);
        this.activePlayer = activePlayer;
        this.card = card;
        this.bust = bust;
    }
}
