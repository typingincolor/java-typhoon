package com.laterooms.route;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

public class ProcessEmailRoute extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from("rabbitmq://localhost/camel?queue=comms.email&durable=true&autoDelete=false&username=guest&password=guest")
                .convertBodyTo(String.class)
                .removeHeaders("*")
                .setHeader("to").jsonpath("$.to")
                .setHeader("subject").jsonpath("$.subject")
                .to("freemarker:email.ftl")
                .to("smtp://localhost:8025");
    }
}
