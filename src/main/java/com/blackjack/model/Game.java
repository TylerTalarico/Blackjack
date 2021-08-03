package com.blackjack.model;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> playerList;
    private Player activePlayer;
    private Deck deck;
    private int pointCap = 0;
    private Player winner = null;
    private boolean isStarted = false;


    public Game(ArrayList<Player> list, int pointCap) {

        this.playerList = list;
        this.pointCap = pointCap;
        this.activePlayer = list.get(0);
        this.deck = Deck.newDeck();
    }


    public boolean isStarted() {
        return isStarted;
    }

    public void start() {
        this.isStarted = true;
    }
}
