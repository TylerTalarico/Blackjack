package com.blackjack;

import java.io.InputStream;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.google.gson.Gson;
import spark.TemplateEngine;
import com.blackjack.application.WebServer;
import spark.template.freemarker.FreeMarkerEngine;

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

        final TemplateEngine templateEngine = new FreeMarkerEngine();

        final WebServer webServer = new WebServer(gson, templateEngine);

        // inject web server into application
        final Application app = new Application(webServer);

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
