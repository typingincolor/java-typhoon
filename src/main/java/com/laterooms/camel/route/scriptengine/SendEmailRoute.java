package com.laterooms.camel.route.scriptengine;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.laterooms.typhoon.dto.ScriptDTO;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by andrew on 17/04/2014.
 */
@Component
public class SendEmailRoute extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:send_email")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        char current = exchange.getIn().getHeader("step", Character.class);
                        ScriptDTO script = exchange.getIn().getHeader("script", ScriptDTO.class);

                        Gson gson = new Gson();

                        String command = gson.toJson(script.getScript());
                        String to = JsonPath.read(command, "$." + current + ".data.to");
                        String subject = JsonPath.read(command, "$." + current + ".data.subject");

                        current++;

                        exchange.getIn().setHeader("to", to);
                        exchange.getIn().setHeader("subject", subject);
                        exchange.getIn().setHeader("step", current);
                    }
                })
                .to("smtp://localhost:8025")
                .end();
    }
}
