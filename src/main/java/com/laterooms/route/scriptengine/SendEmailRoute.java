package com.laterooms.route.scriptengine;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by andrew on 17/04/2014.
 */
@Component
public class SendEmailRoute extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:send_email")
                .to("log:com.laterooms?level=DEBUG&showStreams=true");
    }
}
