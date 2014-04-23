package com.laterooms.route;

import com.laterooms.entity.Task;
import com.laterooms.repository.TaskRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.restlet.Response;
import org.restlet.data.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DeleteAtRequest extends SpringRouteBuilder {
    @Autowired
    TaskRepository taskRepository;

    @Value("${restlet.port}")
    private int port;

    @Override
    public void configure() throws Exception {
        from(String.format("restlet:http://0.0.0.0:%d/at/{id}?restletMethods=DELETE", port))
                .streamCaching()
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Task task = taskRepository.get(exchange.getIn().getHeader("id", Integer.class));
                        taskRepository.delete(task);
                        exchange.getOut().setBody("OK");
                    }
                })
                .marshal().json(JsonLibrary.Gson)
                .setHeader("Content-Type", constant("text/plain"));
    }
}
