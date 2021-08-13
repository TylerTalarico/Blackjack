package com.blackjack.application;

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
        else if (!nameIsValid(name))
            return SignInResult.NAME_INVALID;
        else {

            Player newPlayer = new Player(name);
            playerList.put(name, newPlayer);
            user.attribute("player", newPlayer);
            return SignInResult.SUCCESS;
        }
    }

    public static void signOut(String name, Session user) {
        playerList.remove(name);
        user.removeAttribute("player");
    }

    private static boolean nameIsValid(String name) {
        if (name.length() < 4 || name.length() > 11)
            return false;

        for (int i = 0; i < name.length(); i++) {
            int charIndex = name.charAt(i);

            // Each character can only be a letter or number
            if (charIndex < 48 || (charIndex > 57 && charIndex < 65) || (charIndex > 90 && charIndex < 97) || charIndex > 122)
                return false;
        }

        return true;
    }

    public static Player getPlayer(String playerName) {
        return playerList.get(playerName);
    }



}