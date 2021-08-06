package com.blackjack.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

public class Deck {
    private final Stack<Card> cards;

    public static Deck newDeck() {
        String[] faceCards = {"J", "Q", "K"};
        Stack<Card> cards = new Stack<>();
        for (Card.Suit suit: Card.Suit.values()) {
            for (int value = 2; value <= 11; value++) {
                cards.push(new Card(suit, value));
            }
            for (String faceCard : faceCards) {
                cards.push(new Card(suit, faceCard));
            }
        }

        Deck deck = new Deck(cards);
        deck.shuffle();
        return deck;

    }

    public static Deck emptyDeck() {
        return new Deck(new Stack<>());
    }

    private Deck (Stack<Card> cards) {
        this.cards = cards;
    }

    private void shuffle() {
        Stack<Card> shuffledCards = new Stack<>();
        int limit = cards.size();
        for (int i = 0; i < limit; i++) {
            shuffledCards.push(cards.remove((int)(Math.random() * (cards.size()))));
        }
        this.cards.addAll(shuffledCards);
    }

    public Card drawCard() {
        return cards.pop();
    }
}
