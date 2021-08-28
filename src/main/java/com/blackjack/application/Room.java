package com.blackjack.application;


import com.blackjack.model.Game;
import com.blackjack.model.Player;
import com.blackjack.util.*;
import com.blackjack.util.GameUpdate.*;
import com.blackjack.util.LobbyUpdate.RoomNumberPlayersUpdate;

import com.google.gson.Gson;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.websocket.api.Session;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;


public class Room {


    // ArrayBLockingQueue is thread safe
    private final ArrayBlockingQueue<Player> playerList;
    private final ConcurrentHashSet<Session> sessionList = new ConcurrentHashSet<>();
    private Player host;
    private final int playerCap;
    private final String roomName;
    private final Game game;
    private final Gson gson = new Gson();
    private boolean roomIsOpen = true;


    /**
     * Room Constructor
     *
     * @param host
     *      host {@link Player} of the room (can start the game)
     * @param playerCap
     *      max players in a room
     * @param pointCap
     *      number of points to win a game
     * @param roomName
     *      name of the room
     */
    public Room(Player host, int playerCap, int pointCap, String roomName) {
        this.playerCap = playerCap;
        this.playerList = new ArrayBlockingQueue<>(playerCap, true);
        this.roomName = roomName;
        this.host = host;
        this.playerList.add(host);
        this.game = new Game(playerList, pointCap);
    }


    /**
     * Add a user to a room
     *
     * @param user
     *      WebSocket Session of the user
     * @param player
     *      user's Player object
     */
    public synchronized void addUser(Session user, Player player) {

        // Logic to determine if player can join the room
        if (player != null && playerList.size() < playerCap && roomIsOpen && !playerList.contains(player)) {
            playerList.add(player);
            WebSocketSessionManager.updateAllClients(new RoomNumberPlayersUpdate(roomName, playerList.size()));
        }
        // If the player cap is reached, close the room
        if (playerList.size() == playerCap)
            roomIsOpen = false;
        sessionList.add(user);
        updatePlayerList();
    }

    /**
     * Remove user from a room
     *
     * @param user
     *      WebSocket session {@link Session} of the user
     * @param player
     *      user's Player object
     */
    public synchronized void removeUser(Session user, Player player) {


        sessionList.remove(user);
        playerList.remove(player);

        // If the player leaving affects the game,
        // then update the game state

        if (this.playerList.isEmpty())
            RoomManager.removeRoom(this.roomName);

        else if(this.playerList.size() == 1 && game.isStarted())
            updateGameState(new GameOverUpdate(playerList.peek()));

        if (this.host.equals(player) && !this.playerList.isEmpty())
            this.host = playerList.peek();

        if (playerList.size() < playerCap && !game.isStarted())
            this.roomIsOpen = true;

        // Sends data to update the corresponding room UI element
        updatePlayerDisconnect(player);
        WebSocketSessionManager.updateAllClients(new RoomNumberPlayersUpdate(roomName, playerList.size()));
    }

    /**
     * Starts the round
     */
    public void startRound() {

        if (playerList.size() == 1)
            return;

        //TODO: Send an error message if the round was not started

        System.out.println("Player List: " + playerList);

        game.startRound();
        updateGameState(new ClearHandUpdate());


        // Deals 2 cards to each player
        for (int i = 0; i < 2; i++) {
            for (Player p: playerList) {
                DealUpdate du = game.deal(p);

                updateGameState(du);

                // Insert a 300ms delay between each card
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                }catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }

        updateGameState(new StartRoundUpdate(game.getActivePlayer()));
        System.out.println("Player Starting the Round: " + game.getActivePlayer().getName());


    }

    /**
     * Perform a certain action (either HIT or STAND)
     * This could be expanded to include other actions, such as splitting
     *
     * @param action
     *      action to be performed
     * @param playerSubmitting
     *      player making the move
     */
    public void performAction(Game.ActionType action, Player playerSubmitting) {

        if (!playerSubmitting.equals(game.getActivePlayer()))
            return;

        GameUpdate playerMove = game.performAction(action);

        updateGameState(playerMove);

        if (game.isCurrentRoundOver()) {
            updateGameState(new RoundOverUpdate(game.getRoundWinner()));

            try {
                TimeUnit.MILLISECONDS.sleep(5000);
            }catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            if (game.getGameWinner() != null) {
                updateGameState(new GameOverUpdate(game.getGameWinner()));
            }
            else {
                startRound();
            }
        }
    }


    /**
     * Send a game update to the UI
     *
     * @param gu
     *      GameUpdate object being sent to the client
     */
    private void updateGameState(GameUpdate gu) {
        for (Session s: sessionList) {
            try {
                s.getRemote().sendString(gson.toJson(gu));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return
     *      name of the Room
     */
    public String getRoomName() {
        return this.roomName;
    }

    /**
     * @return
     *      Player object of the host user
     */
    public Player getHost() {
        return this.host;
    }

    /**
     * @return
     *      information used by the UI to create and update room elements
     */
    public RoomData getRoomData() {
        return new RoomData(roomName, playerCap, playerList.size(), game.getPointCap());
    }

    /**
     * update the client with new player data
     */
    private void updatePlayerList() {
        PlayerListMessage plm = new PlayerListMessage(playerList);
        for (Session s: sessionList) {
            try {
                s.getRemote().sendString(gson.toJson(plm));
                System.out.println(gson.toJson(plm));
                System.out.println("Player List in Room " + roomName + " Updated");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param player
     *      Player {@link Player} disconnecting from the site
     */
    private void updatePlayerDisconnect(Player player) {
        PlayerDisconnectUpdate pdm = new PlayerDisconnectUpdate(player, this.game.getActivePlayer());
        for (Session s: sessionList) {
            try {
                s.getRemote().sendString(gson.toJson(pdm));
                System.out.println(gson.toJson(pdm));
                System.out.println("Player List in Room " + roomName + " Updated");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
