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
        calculateHandTotal();

    }

    public int getHandTotal() {
       return this.handTotal;
    }

    private void calculateHandTotal() {
        int newTotal = 0;
        for (Card c: this.cards) {
            newTotal += c.value();
        }

        for (Card card: this.cards) {
            if (card.id().equals("A") && newTotal > 21) {
                newTotal -= 10;
            }
        }

        handTotal = newTotal;
    }




}
