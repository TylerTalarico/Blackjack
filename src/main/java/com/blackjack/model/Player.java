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

    public String toString() {
        return this.name;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            return ((Player) obj).getName().equals(this.name);
        }
        return false;
    }
}
