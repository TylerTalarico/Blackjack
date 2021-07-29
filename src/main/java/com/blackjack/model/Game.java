package com.blackjack.model;

import java.util.ArrayList;

public class Game {

    ArrayList<Player> playerList;
    Player activePlayer;
    int pointCap = 0;
    Player winner = null;


    public Game(ArrayList<Player> list, int cap) {

        this.playerList = list;
        this.pointCap = cap;
        this.activePlayer = list.get(1);
    }
}
