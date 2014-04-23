package com.laterooms.route;

import com.google.gson.Gson;
import com.laterooms.entity.Task;
import com.laterooms.json.AtPostResponse;
import com.laterooms.json.AtRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by andrew on 15/04/2014.
 */
@Component
public class PostAtRequest extends SpringRouteBuilder {
    private Gson gson = new Gson();

    @Value("${restlet.port}")
    private int port;

    @Value("${hostname}")
    private String hostname;

    @Override
    public void configure() throws Exception {
        from(String.format("restlet:http://0.0.0.0:%d/at?restletMethods=POST", port))
                .streamCaching()
                .unmarshal().json(JsonLibrary.Gson, AtRequest.class)
                .to("jpa://com.laterooms.entity.Task")
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
