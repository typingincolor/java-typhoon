package com.laterooms.camel.route;

import com.google.gson.Gson;
import com.laterooms.typhoon.entity.Task;
import com.laterooms.camel.json.AtPostResponse;
import com.laterooms.camel.json.AtRequest;
import com.laterooms.typhoon.repository.TaskRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AtServiceRoutes extends SpringRouteBuilder {
    @Autowired
    TaskRepository taskRepository;

    @Value("${restlet.port}")
    private int port;

    @Value("${hostname}")
    private String hostname;

    private Gson gson = new Gson();

    @Override
    public void configure() throws Exception {
        from(String.format("restlet:http://0.0.0.0:%d/at/unprocessed?restletMethods=GET", port))
                .streamCaching()
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody(taskRepository.findAllUnprocessed());
                    }
                })
                .marshal().json(JsonLibrary.Gson)
                .setHeader("Content-Type", constant("application/json"));

        from(String.format("restlet:http://0.0.0.0:%d/at/{id}?restletMethods=GET", port))
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

        from(String.format("restlet:http://0.0.0.0:%d/at?restletMethods=POST", port))
                .streamCaching()
                .unmarshal().json(JsonLibrary.Gson, AtRequest.class)
                .to("jpa://com.laterooms.typhoon.entity.Task")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
                        Task task = (Task) exchange.getIn().getBody();
                        response.setStatus(Status.SUCCESS_ACCEPTED);

                        AtPostResponse atPostResponse = new AtPostResponse(String.format("http://%s:%d/at/%d", hostname, port, task.getId()));
                        String json = gson.toJson(atPostResponse);

                        response.setEntity(json, MediaType.APPLICATION_JSON);
                        exchange.getOut().setBody(response);
                    }
                });
    }
}
