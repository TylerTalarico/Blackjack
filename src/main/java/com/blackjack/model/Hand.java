package com.blackjack.model;

import java.util.ArrayList;

public class Hand {

    private final ArrayList<Card> cards;
    private int handTotal;

    public Hand() {
        this.cards = new ArrayList<>();
        this.handTotal = 0;
    }

    public void addCard(Card card) {
        this.cards.add(card);
        handTotal += card.value();
        if (containsAce() && handTotal > 21)
            handTotal -= 10;
    }

    public int getHandTotal() {
       return this.handTotal;
    }

    private boolean containsAce() {
        for (Card card: this.cards) {
            if (card.id().equals("A"))
                return true;
        }
        return false;
    }




}
