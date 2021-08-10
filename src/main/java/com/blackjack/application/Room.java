package com.blackjack.application;


import com.blackjack.model.Game;
import com.blackjack.model.Player;
import com.blackjack.util.*;
import com.blackjack.util.PlayerConnectionMessage.ConnectionType;
import static com.blackjack.util.PlayerConnectionMessage.ConnectionType.*;

import com.google.gson.Gson;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;


public class Room {

    private class RoomData {
        private String roomName;
        private Collection<Player> playerList;
        private int playerCap;
        private int pointCap;

        public RoomData(String rn, Collection<Player> pl, int playerCap, int pointCap) {
            this.roomName = rn;
            this.playerList = pl;
            this.playerCap = playerCap;
            this.pointCap = pointCap;
        }
    }


    private final ArrayBlockingQueue<Player> playerList;
    private final ConcurrentHashSet<Session> sessionList = new ConcurrentHashSet<>();
    private Player host;
    private final int playerCap;
    private final String roomName;
    private final Game game;
    private final Gson gson = new Gson();
    private boolean roomIsOpen = true;

    private RoomData roomData;

    public Room(Player host, int playerCap, int pointCap, String roomName) {
        this.playerCap = playerCap;
        this.playerList = new ArrayBlockingQueue<>(playerCap, true);
        this.roomName = roomName;
        this.host = host;
        this.playerList.add(host);
        this.game = new Game(playerList, pointCap);

        this.roomData = new RoomData(roomName, playerList, playerCap, pointCap);
    }


    public synchronized void addUser(Session user, Player player) {
        if (player != null && playerList.size() < playerCap && roomIsOpen && !playerList.contains(player)) {
            playerList.add(player);
        }
        if (playerList.size() == playerCap)
            roomIsOpen = false;
        sessionList.add(user);
        updatePlayerList();

    }


    public synchronized void removeUser(Session user, Player player) {
        sessionList.remove(user);
        playerList.remove(player);

        if (playerList.size() < playerCap && !game.isStarted())
            this.roomIsOpen = true;
        updatePlayerList(DISCONNECT, player);
    }

    public void startRound() {

        System.out.println("Player List: " + playerList);

        game.startRound();

        for (Session session: sessionList) {
            updateGameState(new ClearHandUpdate());
        }

        for (int i = 0; i < 2; i++) {
            for (Player p: playerList) {
                DealUpdate du = game.deal(p);

                updateGameState(du);
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

    public Collection<Player> getPlayerList() {
        return this.playerList;
    }


    private void updateGameState(GameUpdate gu) {
        for (Session s: sessionList) {
            try {
                s.getRemote().sendString(gson.toJson(gu));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getRoomName() {
        return this.roomName;
    }

    public Player getHost() {
        return this.host;
    }

    public GameUpdate makeMove(Game.ActionType move) {
        return this.game.performAction(move);
    }

    public RoomData getRoomData() { return this.roomData; }

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

    private void updatePlayerList(ConnectionType ct, Player player) {
        PlayerConnectionMessage pcm = new PlayerConnectionMessage(ct, player);
        for (Session s: sessionList) {
            try {
                s.getRemote().sendString(gson.toJson(pcm));
                System.out.println(gson.toJson(pcm));
                System.out.println("Player List in Room " + roomName + " Updated");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




}
