package com.laterooms.camel.route.scriptengine;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.laterooms.typhoon.dto.ScriptDTO;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.freemarker.FreemarkerConstants;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * Created by andrew on 17/04/2014.
 */
@Component
public class ApplyTemplateRoute extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:apply_template")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        char current = exchange.getIn().getHeader("step", Character.class);
                        ScriptDTO script = exchange.getIn().getHeader("script", ScriptDTO.class);

                        Gson gson = new Gson();

                        String command = gson.toJson(script.getScript());
                        String template = JsonPath.read(command, "$." + current + ".data.template");
                        Map<?, ?> templateData = JsonPath.read(command, "$." + current + ".data.template_data");

                        current++;

                        exchange.getIn().setHeader(FreemarkerConstants.FREEMARKER_RESOURCE_URI, template + ".ftl");
                        exchange.getIn().setHeader("freemarker_template_data", gson.toJson(templateData));
                        exchange.getIn().setHeader("step", current);
                    }
                })
                .to("freemarker:email.ftl")
                .end();
    }
}
