package com.laterooms.camel.json;

import java.util.Map;

/**
 * Created by abraithwaite on 17/04/2014.
 */
public class FactoryRequest {
    private String action;
    private Map<String, Object> data;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
