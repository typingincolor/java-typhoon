package com.laterooms.typhoonweb.service;

import org.json.simple.JSONObject;

/**
 * Created by andrew on 26/04/2014.
 */
public interface JMXService {
    public JSONObject getRouteStates();
    public JSONObject getContextStats();
}
