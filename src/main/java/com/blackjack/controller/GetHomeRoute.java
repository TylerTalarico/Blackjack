package com.blackjack.controller;

import com.blackjack.model.Player;
import freemarker.template.Configuration;
import freemarker.template.Version;
import spark.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GetHomeRoute implements Route {

    public static String PLAYER_ATTR = "player";
    public static String HOME_VIEW_NAME = "home.ftl";

    private TemplateEngine templateEngine;

    public GetHomeRoute(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {

        Session httpSession = request.session();
        Player currentUser = httpSession.attribute(PLAYER_ATTR);
        return templateEngine.render(getHomePage(currentUser));
    }

    public static ModelAndView getHomePage(Player currentUser) {

        Map<String, Object> vm = new HashMap<>();
        vm.put(PLAYER_ATTR, currentUser);

        return new ModelAndView(vm, HOME_VIEW_NAME);
    }
}
