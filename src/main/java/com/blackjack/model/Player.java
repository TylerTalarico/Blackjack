package com.blackjack.model;

import org.eclipse.jetty.websocket.api.Session;

public class Player {

    private final String name;
    private int pointCount = 0;
    private final Hand hand = new Hand();


    public Player (String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public boolean equals(Player player) {
        return player.getName().equals(this.name);
    }

    public boolean isBust() {
        return this.hand.getHandTotal() > 21;
    }

    public void deal(Card card) {
        this.hand.addCard(card);
    }

    public int getHandTotal() {
        return this.hand.getHandTotal();
    }
}
