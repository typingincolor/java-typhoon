package com.laterooms.route;

import com.laterooms.repository.TaskRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GetUprocessedAtRequest extends SpringRouteBuilder {
    @Autowired
    TaskRepository taskRepository;

    @Value("${restlet.port}")
    private int port;

    @Override
    public void configure() throws Exception {
        from(String.format("restlet:http://0.0.0.0:%d/at/unprocessed?restletMethods=GET", port))
                .startupOrder(1)
                .streamCaching()
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody(taskRepository.findAllUnprocessed());
                    }
                })
                .marshal().json(JsonLibrary.Gson)
                .setHeader("Access-Control-Allow-Origin", constant("*"))
                .setHeader("Content-Type", constant("application/json"));
    }
}
