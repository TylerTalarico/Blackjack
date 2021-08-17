package com.blackjack.controller;

import com.blackjack.application.PlayerServices;
import com.blackjack.application.WebServer;
import com.blackjack.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostSignOutRoute implements Route {

    public PostSignOutRoute() { }


    @Override
    public Object handle(Request request, Response response) throws Exception {



        Session httpSession = request.session();
        Player player = httpSession.attribute("player");

        if (player == null) {
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        PlayerServices.signOut(player.getName(), httpSession);
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
