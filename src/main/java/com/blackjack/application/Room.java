package com.blackjack.application;


import com.blackjack.model.Game;
import com.blackjack.model.Player;
import com.blackjack.util.Message;
import com.blackjack.util.PlayerConnectionMessage;
import com.blackjack.util.PlayerConnectionMessage.ConnectionType;
import static com.blackjack.util.PlayerConnectionMessage.ConnectionType.*;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
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


    public synchronized void addUser(Session user, Player player) {
        if (player != null && playerList.size() < playerCap) {
            playerList.add(player);
            sessionList.add(user);
        }
        updatePlayerList(CONNECT, player);
    }


    public synchronized void removeUser(Session user, Player player) {
        sessionList.remove(user);
        playerList.remove(player);
        updatePlayerList(DISCONNECT, player);
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

    public Game.MoveResult makeMove(Game.MoveType move) {
        return this.game.makeMove(move);
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
