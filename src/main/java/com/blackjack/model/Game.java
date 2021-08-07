package com.blackjack.model;

import com.blackjack.util.DealUpdate;
import com.blackjack.util.GameUpdate;
import com.blackjack.util.HitUpdate;
import com.blackjack.util.StandUpdate;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Game {

    public enum ActionType {
        HIT,
        STAND,
        DEAL
    }

    public enum MoveResult {
        BUST,
        NOT_BUST
    }

    private final Gson gson = new Gson();

    private final Collection<Player> playerList;
    private final Iterator<Player> playerIterator;
    private Player activePlayer;


    private Deck deck;
    private int pointCap = 0;
    private Player winner = null;
    private boolean isStarted = false;
    private boolean currentRoundOver = false;


    public Game(Collection<Player> list, int pointCap) {

        this.playerList = list;
        this.playerIterator = this.playerList.iterator();
        this.activePlayer = playerIterator.next();
        this.pointCap = pointCap;
        this.deck = Deck.newDeck();
    }


    public boolean isStarted() {
        return isStarted;
    }


    public void startRound() {

        if (!this.isStarted)
            this.isStarted = true;

        if (!this.currentRoundOver)
            return;

        this.deck = Deck.newDeck();
        for (Player player: playerList)
            player.clearHand();

    }



    public GameUpdate performAction(ActionType move) {

        GameUpdate result;
        switch(move) {
            case HIT:
                result = hit();

            default:
                result = stand();

        }
        if (!playerIterator.hasNext())
            this.currentRoundOver = true;
        return result;
    }

    private HitUpdate hit() {
        Card cardDrawn = deck.drawCard();
        activePlayer.deal(cardDrawn);

        if (activePlayer.isBust()) {
            Player bustedPlayer = activePlayer;
            this.activePlayer = playerIterator.next();
            return new HitUpdate(bustedPlayer, activePlayer, cardDrawn, true);
        }

        return new HitUpdate(activePlayer, activePlayer, cardDrawn, true);
    }

    private StandUpdate stand() {
        this.activePlayer = playerIterator.next();
        return new StandUpdate(activePlayer);
    }

    public DealUpdate deal(Player player) {
        Card nextCard = deck.drawCard();
        player.deal(nextCard);
        return new DealUpdate(player, nextCard);
    }

    public Player getRoundWinner() {
        int highestHand = 0;
        Player roundWinner = null;
        if (playerIterator.hasNext())
            return null;
        for (Player player: playerList) {
            if (player.getHandTotal() > highestHand && player.getHandTotal() <= 21) {
                highestHand = player.getHandTotal();
                roundWinner = player;
            }
        }
        return roundWinner;
    }

    public boolean isCurrentRoundOver() {
        return currentRoundOver;
    }


}
