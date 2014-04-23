package com.laterooms.route;

import com.laterooms.dto.ScriptDTO;
import com.laterooms.service.ScriptFactory;
import com.laterooms.json.FactoryRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ScriptFactoryRoute extends SpringRouteBuilder {
    @Autowired
    ScriptFactory scriptFactory;

    @Value("${restlet.port}")
    private int port;

    @Value("${hostname}")
    private String hostname;

    @Override
    public void configure() throws Exception {
        from(String.format("restlet:http://0.0.0.0:%d/script/factory?restletMethods=POST", port))
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
                        result.put("url", String.format("http://%s:%d/script/%d", hostname, port, script.getId()));
                        result.put("run", String.format("http://%s:%d/script/%d/run", hostname, port, script.getId()));
                        result.put("id", script.getId());
                        exchange.getOut().setBody(result);
                    }
                })
                .marshal().json(JsonLibrary.Gson)
                .setHeader("Content-Type", constant("application/json"));
    }
}
