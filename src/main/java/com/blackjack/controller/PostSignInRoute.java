package com.blackjack.controller;

import com.blackjack.application.WebServer;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostSignInRoute implements Route {

    private final PlayerServices playerServices;

    public PostSignInRoute(PlayerServices ps) {
        this.playerServices = ps;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        String desiredName = request.queryParams("username");
        Session httpSession = request.session();
        PlayerServices.SignInResult result = playerServices.signIn(desiredName, httpSession);
        response.redirect(WebServer.HOME_URL);
        return null;
    }

}
