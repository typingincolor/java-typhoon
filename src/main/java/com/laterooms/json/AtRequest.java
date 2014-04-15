package com.laterooms.json;

import java.util.Date;

/**
 * Created by andrew on 15/04/2014.
 */
public class AtRequest {
    private Date at;
    private String url;

    public Date getAt() {
        return at;
    }

    public void setAt(Date at) {
        this.at = at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
