package com.blackjack.model;

public class Card {
    private final int value;
    private final String id;
    private final Suit suit;

    public Card(Suit suit, int value) {
        this.value = value;
        this.suit = suit;
        if (value == 11)
            this.id = "A";
        else
            this.id = String.valueOf(value);
    }

    public Card(Suit suit, String id) {
        this.value = 10;
        this.suit = suit;
        this.id = id;
    }

    public enum Suit {
        SPADE,
        HEART,
        CLUB,
        DIAMOND
    }

    public int value() {
        return this.value;
    }
}
