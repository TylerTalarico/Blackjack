package com.blackjack.application;

import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.websocket.api.Session;
import java.util.Set;

public class SessionManager {

    private static Set<Session> sessions = new ConcurrentHashSet<Session>();

    public static void updateClients() {
        for (Session s: sessions) {
            try {
                s.getRemote().sendString(RoomManager.getNumRooms());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void addSession(Session user) {
        try {
            sessions.add(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void deleteSession(Session user) {
        try {
            sessions.remove(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
