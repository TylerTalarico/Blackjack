package com.blackjack.application;

import com.blackjack.util.LobbyUpdate.LobbyUpdate;
import com.blackjack.util.LobbyUpdate.RoomListUpdate;
import com.blackjack.util.PlayerListMessage;
import com.google.gson.Gson;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;


public class WebSocketSessionManager {

    private final static String ROOM_UPDATE = "roomUpdate";

    private final static ConcurrentHashSet<Session> users = new ConcurrentHashSet<>();
    private final static Gson gson = new Gson();

    public static void addUser(Session user) {
        try {
            users.add(user);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeUser(Session user) {
        try {
            users.remove(user);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateAllClients(LobbyUpdate lu) {

        for (Session s: users) {
            try {
                s.getRemote().sendString(gson.toJson(lu));

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateUser(Session user) {
        try {
            user.getRemote().sendString(gson.toJson(new RoomListUpdate(RoomManager.getAllRoomData())));
            System.out.println(gson.toJson(new RoomListUpdate(RoomManager.getAllRoomData())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
