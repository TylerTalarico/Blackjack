package com.blackjack.application;

import static spark.Spark.*;

import java.util.Objects;
import java.util.logging.Logger;

import com.blackjack.controller.*;
import com.google.gson.Gson;
import spark.TemplateEngine;


public class WebServer {
    private static final Logger LOG = Logger.getLogger(WebServer.class.getName());

    private final TemplateEngine templateEngine;
    private final Gson gson;

    //
    // Constants
    //

    public static final String HOME_URL = "/";

    public static final String SIGN_IN_URL = "/signin";

    public static final String ROOM_URL = "/room";

    public static final String CREATE_ROOM_URL = "/createRoom";

    public static final String SIGN_OUT_URL = "/signOut";


    /**
     * The constructor for the Web Server.
     *
     * @param te
     *    The default TemplateEngine to render page-level HTML views.
     * @param gson
     *    The Google JSON parser object used to render Ajax responses.
     *
     * @throws NullPointerException
     *    If any of the parameters are {@code null}.
     */
    public WebServer(Gson gson, TemplateEngine te) {
        this.gson = Objects.requireNonNull(gson, "gson cannot be null");
        this.templateEngine = Objects.requireNonNull(te, "templateEngine cannot be null");

    }


    /**
     * Initialize all of the HTTP routes that make up this web application.
     *
     * <p>
     * Initialization of the web server includes defining the location for static
     * files, and defining all routes for processing client requests. The method
     * returns after the web server finishes its initialization.
     * </p>
     */
    public void initialize() {

        // Configuration to serve static files
        staticFileLocation("/public");



        get(HOME_URL, new GetHomeRoute(templateEngine));

        get(ROOM_URL, new GetRoomRoute(templateEngine));

        post(CREATE_ROOM_URL, new PostCreateRoomRoute(templateEngine));

        post(SIGN_IN_URL, new PostSignInRoute());

        post(SIGN_OUT_URL, new PostSignOutRoute());


        LOG.config("WebServer is initialized.");
    }
}

