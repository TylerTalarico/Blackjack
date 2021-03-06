package com.blackjack.controller;

import com.blackjack.application.PlayerServices;
import com.blackjack.application.WebServer;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostSignInRoute implements Route {

    public PostSignInRoute() {

    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        String desiredName = request.queryParams("username");
        Session httpSession = request.session();
        PlayerServices.SignInResult result = PlayerServices.signIn(desiredName, httpSession);
        response.redirect(WebServer.HOME_URL);
        return null;
    }

}
