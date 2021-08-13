package com.blackjack.model;

public class Player {

    private final String name;
    private int pointCount = 0;
    private Hand hand = new Hand();


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

    public void givePoint() { this.pointCount++; }

    public int getHandTotal() {
        return this.hand.getHandTotal();
    }

    public int getPointCount() { return this.pointCount; }

    public void clearHand() {
        this.hand = new Hand();
    }

    public void resetPoints() {
        this.pointCount = 0;
    }
}
