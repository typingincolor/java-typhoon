package com.laterooms.route;

import com.laterooms.dto.ScriptDTO;
import com.laterooms.service.ScriptFactory;
import com.laterooms.json.FactoryRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ScriptFactoryRoute extends SpringRouteBuilder {
    @Autowired
    ScriptFactory scriptFactory;

    @Override
    public void configure() throws Exception {
        from("restlet:http://0.0.0.0:8080/script/factory?restletMethods=POST")
                .streamCaching()
                .startupOrder(2)
                .unmarshal().json(JsonLibrary.Gson, FactoryRequest.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        ScriptDTO script = scriptFactory.build(exchange.getIn().getBody(FactoryRequest.class));
                        exchange.getOut().setBody(script);
                    }
                })
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        ScriptDTO script = exchange.getIn().getBody(ScriptDTO.class);

                        Map<String, Object> result = new HashMap<String, Object>();
                        result.put("url", "http://localhost:8080/script/" + script.getId());
                        result.put("run", "http://localhost:8080/script/" + script.getId() + "/run");
                        result.put("id", script.getId());
                        exchange.getOut().setBody(result);
                    }
                })
                .marshal().json(JsonLibrary.Gson)
                .setHeader("Content-Type", constant("application/json"));
    }
}
