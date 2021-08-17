package com.blackjack.application;

import com.blackjack.model.Player;
import spark.Session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Pure Fabrication controller used to store and manage
 * Player objects throughout the application
 */
public class PlayerServices {

    /**
     * enum used for checking the status of a sign-in
     */
    public enum SignInResult {
        NAME_TAKEN,
        NAME_INVALID,
        SUCCESS
    }

    // ConcurrentHashMap used to store all players (String playerName used for each key)
    // Concurrent Collections are thread-safe
    private static final ConcurrentHashMap<String, Player> playerList = new ConcurrentHashMap<>();


    /**
     * Used to sign in a player if the name is available
     *
     * @param name  desired name
     * @param user  HTTPSession used to keep track of the Player object created
     * @return  result of the SignIn
     */
    public static SignInResult signIn(String name, Session user) {
        if (playerList.containsKey(name))
            return SignInResult.NAME_TAKEN;

        else if (!nameIsValid(name))
            return SignInResult.NAME_INVALID;

        else {
            // Create the Player object
            Player newPlayer = new Player(name);
            playerList.put(name, newPlayer);
            user.attribute("player", newPlayer);
            return SignInResult.SUCCESS;
        }
    }

    /**
     * Removes the Player from playerList
     *
     * @param name  Name of Player signing out
     * @param user  HTTPSession of user leaving
     */
    public static void signOut(String name, Session user) {
        playerList.remove(name);
        user.removeAttribute("player");
    }

    /**
     * Helper function used to determine if a name is valid
     *
     * @param name  desired name
     * @return  true if the name is valid
     */
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

    /**
     * Get Player from the playerList
     *
     * @param playerName    name of player
     * @return  Player object
     */
    public static Player getPlayer(String playerName) {
        return playerList.get(playerName);
    }



}