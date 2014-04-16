package com.laterooms.route;

import com.laterooms.entity.Task;
import com.laterooms.repository.TaskRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAtRequest extends SpringRouteBuilder {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public void configure() throws Exception {
        from("restlet:http://0.0.0.0:8080/at/{id}?restletMethods=GET")
                .streamCaching()
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Task task = taskRepository.get(exchange.getIn().getHeader("id", Integer.class));
                        exchange.getOut().setBody(task);
                    }
                })
                .marshal().json(JsonLibrary.Gson)
                .setHeader("Content-Type", constant("application/json"));
    }
}
