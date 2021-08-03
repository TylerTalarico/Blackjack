package com.blackjack.application;

import com.blackjack.util.Message;
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

    public static void updateAllClients() {

        for (Session s: users) {
            updateUser(s);
        }
    }

    public static void updateUser(Session user) {
        try {
            user.getRemote().sendString(gson.toJson(RoomManager.getRoomNames()));
            System.out.println(RoomManager.getRoomNames());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
