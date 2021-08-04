package com.blackjack.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class Game {

    public enum MoveType {
        HIT,
        STAND
    }

    public enum MoveResult {
        BUST,
        NOT_BUST
    }

    private final ArrayList<Player> playerList;
    private final Iterator<Player> playerIterator;
    private Player activePlayer;


    private Deck deck;
    private int pointCap = 0;
    private Player winner = null;
    private boolean isStarted = false;


    public Game(ArrayList<Player> list, int pointCap) {

        this.playerList = list;
        this.playerIterator = this.playerList.iterator();
        this.activePlayer = playerIterator.next();
        this.pointCap = pointCap;
        this.deck = Deck.newDeck();
    }


    public boolean isStarted() {
        return isStarted;
    }

    public void start() {
        this.isStarted = true;
    }

    private void deal() {
        for (int i = 0; i < 2; i++) {
            for (Player player: this.playerList) {
                player.deal(deck.drawCard());
            }
        }
    }

    public MoveResult makeMove(MoveType move) {
        switch(move) {
            case HIT:
                return hit();

            default:
                return stand();

        }
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


}
