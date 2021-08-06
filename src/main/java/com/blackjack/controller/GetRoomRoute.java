package com.blackjack.controller;

import com.blackjack.application.Room;
import com.blackjack.application.RoomManager;
import com.blackjack.application.WebServer;
import com.blackjack.model.Player;
import spark.*;

import java.util.HashMap;

public class GetRoomRoute implements Route {

    public static String ROOM_NAME_ATTR = "roomName";
    public static String HOST_ATTR = "hostPlayer";

    private final TemplateEngine templateEngine;

    public GetRoomRoute(TemplateEngine te) {
        this.templateEngine = te;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        Session httpSession = request.session();
        HashMap<String, Object> vm = new HashMap<>();

        Player player = httpSession.attribute(GetHomeRoute.PLAYER_ATTR);
        String roomName = request.queryParams(ROOM_NAME_ATTR);

        Room room = RoomManager.getRoom(roomName);


        if(room == null || player == null) {
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        vm.put(HOST_ATTR, room.getHost());
        vm.put(ROOM_NAME_ATTR, roomName);
        vm.put(GetHomeRoute.PLAYER_ATTR, player);

        return templateEngine.render(new ModelAndView(vm, "room.ftl"));
    }
}
