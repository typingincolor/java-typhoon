package com.laterooms.camel.route;

import org.apache.camel.processor.validation.PredicateValidationException;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CxfRoute extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("cxfrs:http://0.0.0.0:8082/meta-search?resourceClasses=com.laterooms.camel.cxf.Top10Service&bindingStyle=SimpleConsumer")
                .doTry()
                    .validate((org.apache.camel.Expression) simple("${in.header.id.size} > 0"))
                    .to("log:com.laterooms?showBody=true&level=DEBUG")
                .endDoTry()
                .doCatch(PredicateValidationException.class)
                    .setBody(simple("{\"message\": \"${exception.message}\"}"))
                    .setHeader("Content-Type", constant("application/json"))
                    .setHeader("Exchange.HTTP_RESPONSE_CODE", constant(500))
                .end();
    }
}
