package com.laterooms.route;

import com.laterooms.dto.ScriptDTO;
import com.laterooms.dto.ScriptEngineResultDTO;
import com.laterooms.service.ScriptEngine;
import com.laterooms.service.ScriptService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by abraithwaite on 17/04/2014.
 */
@Component
public class ScriptEngineRoute extends SpringRouteBuilder {
    @Autowired
    ScriptService scriptService;

    @Autowired
    ScriptEngine scriptEngine;

    @Override
    public void configure() throws Exception {
        from("restlet:http://0.0.0.0:8080/script/{id}/run?restletMethods=GET")
                .streamCaching()
                .startupOrder(3)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        ScriptDTO script = scriptService.get(exchange.getIn().getHeader("id", Integer.class));
                        exchange.getOut().setBody(script);
                    }
                })
                .to("direct:scriptengine")
                .marshal().json(JsonLibrary.Gson, ScriptDTO.class)
                .setHeader("Content-Type", constant("application/json"));
    }
}
