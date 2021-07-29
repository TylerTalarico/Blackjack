package com.blackjack.controller;

import com.blackjack.application.RoomManager;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class PostCreateRoomRoute implements Route {

    private RoomManager roomManager;

    public PostCreateRoomRoute(RoomManager roomManager) {
        this.roomManager = Objects.requireNonNull(roomManager, "RoomManager must not be null");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        String roomName = request.queryParams("roomName");
        response.redirect("room.html");
        return null;
    }
}
