package com.blackjack;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import spark.ModelAndView;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.IOException;
import java.io.StringWriter;

public class FMTemplateEngine extends TemplateEngine {


    private Configuration configuration;

    public FMTemplateEngine() {
        this.configuration = createDefaultConfiguration();
    }


    @Override
    public String render(ModelAndView modelAndView) {
        try {
            StringWriter stringWriter = new StringWriter();
            Template template = configuration.getTemplate(modelAndView.getViewName());
            template.process(modelAndView.getModel(), stringWriter);
            return stringWriter.toString();
        } catch (IOException | TemplateException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Configuration createDefaultConfiguration() {
        Configuration configuration = new Configuration(new Version(2, 3, 23));
        configuration.setClassForTemplateLoading(FMTemplateEngine.class, "/templates/");
        return configuration;
    }
}
