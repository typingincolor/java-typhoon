package com.laterooms.route;

import com.google.gson.Gson;
import com.laterooms.factory.ScriptDTO;
import com.laterooms.factory.ScriptFactory;
import com.laterooms.json.FactoryRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScriptFactoryRoute extends SpringRouteBuilder {
    @Autowired
    ScriptFactory scriptFactory;

    @Override
    public void configure() throws Exception {
        from("restlet:http://0.0.0.0:8080/script/factory?restletMethods=POST")
                .streamCaching()
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
                        Gson gson = new Gson();
                        String json = gson.toJson(script.getScript());

                        com.laterooms.entity.Script entity = new com.laterooms.entity.Script();
                        entity.setScript(json);

                        exchange.getOut().setBody(entity);
                    }
                })
                .to("jpa://com.laterooms.entity.Script")
                .marshal().json(JsonLibrary.Gson)
                .setHeader("Content-Type", constant("application/json"));
    }
}
