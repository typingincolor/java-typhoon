package com.laterooms.route;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

public class QueueRequestRoute extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from("jetty:http://0.0.0.0:8080/comms")
                .setHeader("rabbitmq.ROUTING_KEY", constant("comms.email"))
                .to("log:com.laterooms?level=DEBUG&showStreams=true")
                .to("rabbitmq://localhost/camel?username=guest&password=guest");
    }
}
