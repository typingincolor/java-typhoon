package com.laterooms.route;

import com.laterooms.repository.TaskRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class GetAllAtRequest extends SpringRouteBuilder {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public void configure() throws Exception {
        from("restlet:http://0.0.0.0:8080/at?restletMethods=GET")
                .streamCaching()
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody(taskRepository.findAllUnprocessed());
                    }
                })
                .marshal().json(JsonLibrary.Gson)
                .setHeader("Content-Type", constant("application/json"));
    }
}
