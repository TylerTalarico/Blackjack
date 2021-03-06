package com.blackjack.model;

public class Card {

    public enum Suit {
        SPADE,
        HEART,
        CLUB,
        DIAMOND
    }


    private final int value;
    private final String id;
    private final Suit suit;
    private boolean flipped = true;

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



    public int value() {
        return this.value;
    }

    public String id() {
        return this.id;
    }

    public boolean equals(Object obj) {
        assert (obj instanceof Card);
        Card card = (Card)obj;
        return card.id.equals(this.id) && card.suit == this.suit && card.value == this.value;
    }

    public void flip() {
        flipped = !flipped;
    }
}
