package com.blackjack.model;

import com.blackjack.util.GameUpdate.DealUpdate;
import com.blackjack.util.GameUpdate.GameUpdate;
import com.blackjack.util.GameUpdate.HitUpdate;
import com.blackjack.util.GameUpdate.StandUpdate;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

public class Game {

    public enum ActionType {
        HIT,
        STAND
    }


    private final ArrayBlockingQueue<Player> playerList;
    private Iterator<Player> playerIterator;
    private Player activePlayer;


    private Deck deck;
    private int pointCap = 0;
    private Player gameWinner = null;
    private boolean isStarted = false;
    private boolean currentRoundOver = false;


    public Game(ArrayBlockingQueue<Player> list, int pointCap) {

        this.playerList = list;
        this.playerIterator = this.playerList.iterator();
        this.activePlayer = null;
        this.pointCap = pointCap;
        this.deck = Deck.newDeck();


    }


    public boolean isStarted() {
        return isStarted;
    }


    public void startRound() {


        this.playerIterator = playerList.iterator();
        this.activePlayer = playerIterator.next();


        if (!this.isStarted) {
            this.isStarted = true;
        }

        if (!this.currentRoundOver)
            return;

        this.deck = Deck.newDeck();
        for (Player player: playerList)
            player.clearHand();
        this.currentRoundOver = false;

    }



    public GameUpdate performAction(ActionType move) {


        GameUpdate result;
        switch(move) {
            case HIT:
                result = hit();
                break;

            default:
                result = stand();
                break;

        }

        if (!playerIterator.hasNext() && activePlayer.isBust())
            this.currentRoundOver = true;

        return result;
    }

    private HitUpdate hit() {
        Card cardDrawn = deck.drawCard();
        activePlayer.deal(cardDrawn);

        if (activePlayer.isBust()) {
            System.out.println(activePlayer + " busted!");
            Player bustedPlayer = activePlayer;

            if (playerIterator.hasNext())
                this.activePlayer = playerIterator.next();
            System.out.println("New Player after Bust: " + activePlayer);
            return new HitUpdate(bustedPlayer, activePlayer, cardDrawn, true);
        }

        System.out.println(activePlayer + " didn't bust");
        return new HitUpdate(activePlayer, activePlayer, cardDrawn, false);
    }

    private StandUpdate stand() {
        Player playerStanding = this.activePlayer;
        if (playerIterator.hasNext())
            this.activePlayer = playerIterator.next();
        else
            currentRoundOver = true;
        return new StandUpdate(playerStanding, activePlayer);
    }

    public DealUpdate deal(Player player) {
        Card nextCard = deck.drawCard();
        player.deal(nextCard);
        return new DealUpdate(player, nextCard);
    }

    public Player getRoundWinner() {
        int highestHand = 0;
        Player roundWinner = activePlayer;
        if (playerIterator.hasNext())
            return null;
        for (Player player: playerList) {
            if (player.getHandTotal() > highestHand && player.getHandTotal() <= 21) {
                highestHand = player.getHandTotal();
                roundWinner = player;
            }
        }
        roundWinner.givePoint();

        if (roundWinner.getPointCount() == pointCap) {
            this.gameWinner = roundWinner;
        }
        return roundWinner;
    }

    public boolean isCurrentRoundOver() {
        return currentRoundOver;
    }

    public Player getActivePlayer() { return this.activePlayer; }

    public Player getGameWinner() {
        return this.gameWinner;
    }

    public int getPointCap() { return this.pointCap; }



}
