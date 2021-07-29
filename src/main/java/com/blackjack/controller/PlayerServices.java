package com.blackjack.controller;

import com.blackjack.model.Player;
import org.eclipse.jetty.util.ConcurrentHashSet;
import spark.Session;

public class PlayerServices {

    public enum SignInResult {
        NAME_TAKEN,
        NAME_INVALID,
        SUCCESS
    }

    private final ConcurrentHashSet<String> playerNameList;

    public PlayerServices(){
        this.playerNameList = new ConcurrentHashSet<>();
    }

    public SignInResult signIn(String name, Session user) {
        if (playerNameList.contains(name))
            return SignInResult.NAME_TAKEN;
        else if (name.equals(""))
            return SignInResult.NAME_INVALID;
        else {
            playerNameList.add(name);
            Player newPlayer = new Player(name);
            user.attribute("player", newPlayer);
            return SignInResult.SUCCESS;
        }
    }



}
