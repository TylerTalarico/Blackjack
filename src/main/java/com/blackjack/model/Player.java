package com.blackjack.model;

import org.eclipse.jetty.websocket.api.Session;

public class Player {

    private final String name;
    private int pointCount = 0;


    public Player (String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
