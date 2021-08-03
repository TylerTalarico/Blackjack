package com.blackjack.controller;

import com.blackjack.model.Player;
import spark.Session;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerServices {

    public enum SignInResult {
        NAME_TAKEN,
        NAME_INVALID,
        SUCCESS
    }

    private static final ConcurrentHashMap<String, Player> playerList = new ConcurrentHashMap<>();


    public static SignInResult signIn(String name, Session user) {
        if (playerList.containsKey(name))
            return SignInResult.NAME_TAKEN;
        else if (name.equals(""))
            return SignInResult.NAME_INVALID;
        else {

            Player newPlayer = new Player(name);
            playerList.put(name, newPlayer);
            user.attribute("player", newPlayer);
            return SignInResult.SUCCESS;
        }
    }

    public static Player getPlayer(String playerName) {
        return playerList.get(playerName);
    }



}