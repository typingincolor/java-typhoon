package com.laterooms.route;

import com.laterooms.dto.ScriptDTO;
import com.laterooms.service.ScriptServiceImpl;
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
public class GetScriptRoute extends SpringRouteBuilder {
    @Autowired
    ScriptServiceImpl scriptService;

    @Override
    public void configure() throws Exception {
        from("restlet:http://0.0.0.0:8080/script/{id}?restletMethods=GET")
                .streamCaching()
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        ScriptDTO script = scriptService.get(exchange.getIn().getHeader("id", Integer.class));
                        exchange.getOut().setBody(script);
                    }
                })
                .marshal().json(JsonLibrary.Gson)
                .setHeader("Content-Type", constant("application/json"));
    }
}
