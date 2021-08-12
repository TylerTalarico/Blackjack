package com.blackjack.controller;

import com.blackjack.application.Room;
import com.blackjack.application.RoomManager;
import com.blackjack.application.WebServer;
import com.blackjack.application.WebSocketSessionManager;
import com.blackjack.model.Player;
import com.blackjack.util.LobbyUpdate.CreateRoomUpdate;
import spark.*;

import java.util.HashMap;

public class PostCreateRoomRoute implements Route {

    private final TemplateEngine templateEngine;

    public PostCreateRoomRoute(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {



        HashMap<String, Object> vm = new HashMap<>();
        Session httpSession = request.session();
        Player host = httpSession.attribute(GetHomeRoute.PLAYER_ATTR);
        String roomName = request.queryParams("roomName");
        int playerCap = Integer.parseInt(request.queryParams("playerCap"));
        int pointCap = Integer.parseInt(request.queryParams("pointCap"));


        if (RoomManager.getRoom(roomName) == null && host != null) {

            Room room = RoomManager.createRoom(roomName, host, playerCap, pointCap);
            if (room != null)
                WebSocketSessionManager.updateAllClients(new CreateRoomUpdate(room.getRoomData()));
            response.redirect(WebServer.ROOM_URL + "?" + GetRoomRoute.ROOM_NAME_ATTR + "=" + roomName);
        }


        return null;

    }
}
