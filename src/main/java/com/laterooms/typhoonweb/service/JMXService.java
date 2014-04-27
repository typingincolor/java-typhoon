package com.laterooms.typhoonweb.service;

import com.laterooms.typhoonweb.DTO.RouteStateDTO;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by andrew on 26/04/2014.
 */
public interface JMXService {
    public JSONObject getRouteStates();
    public JSONObject getContextStats();
}
