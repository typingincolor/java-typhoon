package com.laterooms.route;

import com.laterooms.dto.ScriptDTO;
import com.laterooms.service.ScriptServiceImpl;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by abraithwaite on 17/04/2014.
 */
@Component
public class GetScriptRoute extends SpringRouteBuilder {
    @Autowired
    ScriptServiceImpl scriptService;

    @Value("${restlet.port}")
    private int port;

    @Override
    public void configure() throws Exception {
        from(String.format("restlet:http://0.0.0.0:%d/script/{id}?restletMethods=GET", port))
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
