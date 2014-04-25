package com.laterooms.camel.route;

import com.laterooms.typhoon.dto.ScriptDTO;
import com.laterooms.typhoon.service.ScriptService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by abraithwaite on 17/04/2014.
 */
@Component
public class ScriptEngineRoute extends SpringRouteBuilder {
    @Autowired
    ScriptService scriptService;

    @Value("${restlet.port}")
    private int port;

    @Override
    public void configure() throws Exception {
        from(String.format("restlet:http://0.0.0.0:%d/script/{id}/run?restletMethods=GET", port))
                .startupOrder(3)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        ScriptDTO script = scriptService.get(exchange.getIn().getHeader("id", Integer.class));
                        exchange.getOut().setHeader("script", script);
                        exchange.getOut().setBody(script);
                    }
                })
                .setHeader("step", constant('a'))
                .to("direct:scriptengine")
                .end();
    }
}
