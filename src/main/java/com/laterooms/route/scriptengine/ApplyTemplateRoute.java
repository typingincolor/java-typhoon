package com.laterooms.route.scriptengine;

import com.google.gson.Gson;
import com.laterooms.dto.ScriptDTO;
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

                        Map<String, Object> command = (Map) script.getScript().get(Character.toString(current));
                        Map<String, Object> data = (Map) command.get("data");

                        String template = (String) data.get("template");
                        Map<String, Object> templateData = (Map<String, Object>) data.get("template_data");

                        Gson gson = new Gson();
                        current++;

                        exchange.getOut().setHeader(FreemarkerConstants.FREEMARKER_RESOURCE_URI, template + ".ftl");
                        exchange.getOut().setHeader("freemarker_template_data", gson.toJson(templateData));
                        exchange.getOut().setHeader("step", current);

                    }
                })
                .to("freemarker:email.ftl");
    }
}
