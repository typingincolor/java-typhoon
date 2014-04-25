package com.laterooms.camel.json;

import java.util.Date;

/**
 * Created by andrew on 15/04/2014.
 */
public class AtRequest {
    private Date at;
    private String url;

    public AtRequest(Date at, String url) {
        this.at = at;
        this.url = url;
    }

    public Date getAt() {
        return at;
    }

    public String getUrl() {
        return url;
    }
}
