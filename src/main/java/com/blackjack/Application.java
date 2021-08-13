package com.blackjack;

import java.util.Objects;
import java.util.logging.Logger;

import com.blackjack.application.HomeWebSocketHandler;
import com.blackjack.application.RoomWebSocketHandler;
import com.blackjack.application.PlayerServices;
import com.google.gson.Gson;
import spark.TemplateEngine;
import com.blackjack.application.WebServer;

import static spark.Spark.webSocket;

public class Application {

    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    private final WebServer webServer;




    public static void main(String[] args) {

//        try {
//            ClassLoader classLoader = Application.class.getClassLoader();
//            final InputStream logConfig = classLoader.getResourceAsStream("log.properties");
//            LogManager.getLogManager().readConfiguration(logConfig);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Could not initialize log manager because: " + e.getMessage());
//        }

        // The application uses Gson to generate JSON representations of Java objects.
        // This should be used by your Ajax Routes to generate JSON for the HTTP
        // response to Ajax requests.
        final Gson gson = new Gson();

        final TemplateEngine templateEngine = new FMTemplateEngine();

        final PlayerServices playerServices = new PlayerServices();

        final WebServer webServer = new WebServer(gson, templateEngine, playerServices);

        // inject web server into application
        final Application app = new Application(webServer);


        webSocket("/roomList", HomeWebSocketHandler.class);
        webSocket("/roomJoin", RoomWebSocketHandler.class);


        // start the application up
        app.initialize();
    }


    //
    // Constructor
    //

    private Application(final WebServer webServer) {
        // validation
        Objects.requireNonNull(webServer, "webServer must not be null");
        //
        this.webServer = webServer;
    }

    private void initialize() {
        LOG.config("WebCheckers is initializing.");

        // configure Spark and startup the Jetty web server
        webServer.initialize();

        // other applications might have additional services to configure

        LOG.config("WebCheckers initialization complete.");
    }

}
