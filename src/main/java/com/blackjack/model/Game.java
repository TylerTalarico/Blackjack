package com.blackjack.model;

import com.blackjack.util.GameUpdate;
import com.blackjack.util.InitialDealUpdate;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class Game {

    public enum MoveType {
        HIT,
        STAND
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

    public void start(ArrayList<Session> sessions) {
        this.isStarted = true;
        this.currentRoundOver = false;
        deal(sessions);
    }

    private void deal(ArrayList<Session> sessions) {
        for (int i = 0; i < 2; i++) {
            for (Player player: this.playerList) {
                Card nextCard = deck.drawCard();
                player.deal(nextCard);
                try {
                    for(Session s: sessions) {
                        s.getRemote().sendString(gson.toJson(new InitialDealUpdate(player, nextCard)));
                    }
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public MoveResult makeMove(MoveType move) {

        MoveResult result;
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

    private MoveResult hit() {
        activePlayer.deal(this.deck.drawCard());

        if (activePlayer.isBust()) {
            this.activePlayer = playerIterator.next();
            return MoveResult.BUST;
        }

        return MoveResult.NOT_BUST;
    }

    public MoveResult stand() {
        this.activePlayer = playerIterator.next();
        return MoveResult.NOT_BUST;
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
