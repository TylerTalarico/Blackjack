package com.blackjack.application;


import com.blackjack.model.Game;
import com.blackjack.model.Player;
import com.blackjack.util.Message;
import com.blackjack.util.PlayerListMessage;
import com.google.gson.Gson;
import com.google.gson.internal.bind.util.ISO8601Utils;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Room {


    private final ArrayList<Player> playerList = new ArrayList<>();
    private final ArrayList<Session> sessionList = new ArrayList<>();
    private final int playerCap;
    private final String roomName;
    private final Game game;
    private final Gson gson = new Gson();

    public Room(Player host, int playerCap, int pointCap, String roomName) {
        this.playerCap = playerCap;
        this.roomName = roomName;
        this.playerList.add(host);
        this.game = new Game(playerList, pointCap);
    }

    public synchronized void addPlayer(Player player) {
        if (player != null && playerList.size() < playerCap && !playerList.contains(player))
            playerList.add(player);
    }

    public synchronized void addUser(Session user) {
        assert (user != null && playerList.size() < playerCap);
        sessionList.add(user);
        updatePlayerList();
    }

    public synchronized void removePlayer(Player player) {
        assert (player != null && playerList.size() < playerCap);
        playerList.remove(player);
    }

    public synchronized void removeUser(Session user) {
        assert (user != null && playerList.size() < playerCap);
        sessionList.remove(user);
        updatePlayerList();
    }

    public List<Player> getPlayerList() {
        return this.playerList;
    }


    public void updateGameState(Message msg) {
        for (Session s: sessionList) {
            try {
                s.getRemote().sendString(gson.toJson(msg));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getRoomName() {
        return this.roomName;
    }

    private void updatePlayerList() {
        PlayerListMessage plm = new PlayerListMessage(RoomWebSocketHandler.PLAYER_CONNECTION_MSG, getPlayerList());
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




}
