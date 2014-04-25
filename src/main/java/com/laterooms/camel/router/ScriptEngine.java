package com.laterooms.camel.router;

import org.apache.camel.*;
import org.springframework.stereotype.Component;

/**
 * Created by andrew on 17/04/2014.
 */
@Component
public class ScriptEngine {
    @Consume(uri = "direct:scriptengine")
    @DynamicRouter
    public String route(@Header(Exchange.SLIP_ENDPOINT) String previous) {
        if (previous == null) {
            return "direct:apply_template, direct:send_email";
        }
        else {
            return null;
        }
    }
}
