package com.blackjack.controller;

import spark.Request;
import spark.Response;
import spark.Route;

import java.io.File;

public class GetHomeRoute implements Route {

    public GetHomeRoute() {

    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
        response.redirect("home.html");
        return null;
    }
}
