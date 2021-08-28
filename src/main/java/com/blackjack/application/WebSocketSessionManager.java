package com.blackjack.application;

import com.blackjack.util.LobbyUpdate.LobbyUpdate;
import com.blackjack.util.LobbyUpdate.RoomListUpdate;
import com.google.gson.Gson;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.websocket.api.Session;



public class WebSocketSessionManager {


    // Thread safe Hash Map used to store all user's WebSocket sessions
    // that are on the home page
    private final static ConcurrentHashSet<Session> users = new ConcurrentHashSet<>();

    private final static Gson gson = new Gson();

    /**
     * Add WebSocket session {@link Session} of user joining the site
     *
     * @param user
     *      session of the user
     */
    public static void addUser(Session user) {
        try {
            users.add(user);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove WebSocket session {@link Session} of user joining the site
     *
     * @param user
     *      session of the user
     */
    public static void removeUser(Session user) {
        try {
            users.remove(user);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Send all users {@link Session} the given
     * LobbyUpdate {@link LobbyUpdate} to update
     * each UI
     *
     * @param lu
     *      LobbyUpdate object with relevant data
     */
    public static void updateAllClients(LobbyUpdate lu) {

        for (Session s: users) {
            try {
                s.getRemote().sendString(gson.toJson(lu));

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Update a given user {@link Session} with the
     * new list of rooms {@link Room}
     *
     * @param user
     *      WebSocket session of the user
     */
    public static void updateUser(Session user) {
        try {
            user.getRemote().sendString(gson.toJson(new RoomListUpdate(RoomManager.getAllRoomData())));
            System.out.println(gson.toJson(new RoomListUpdate(RoomManager.getAllRoomData())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
