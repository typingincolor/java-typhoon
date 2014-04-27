package com.laterooms.camel.route;

import com.laterooms.typhoon.entity.Task;
import com.laterooms.typhoon.repository.TaskRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by abraithwaite on 17/04/2014.
 */
@Component
public class RunQueuedJobsRoute extends SpringRouteBuilder {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public void configure() throws Exception {
        from("timer://atq?fixedRate=true&period=15s")
                .streamCaching()
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        List<Task> tasks = taskRepository.findAllReadyToBeProcessed();
                        exchange.getOut().setBody(tasks);
                    }
                })
                .split().body()
                .setHeader(Exchange.HTTP_URI, simple("${body.url}"))
                .setHeader("Task_ID", simple("${body.id}"))
                .setBody(constant(null))
                .to("http://dummyhost")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Integer taskId = exchange.getIn().getHeader("Task_ID", Integer.class);
                        String body = exchange.getIn().getBody(String.class);
                        Integer statusCode = exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
                        Task task = taskRepository.get(taskId);

                        task.setStatusCode(statusCode);
                        task.setResult(body);
                        exchange.getOut().setBody(taskRepository.save(task));
                    }
                })
                .setBody(constant("OK"));
    }
}
