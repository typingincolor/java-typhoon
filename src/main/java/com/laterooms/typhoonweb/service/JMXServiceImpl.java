package com.laterooms.typhoonweb.service;

import org.jolokia.client.J4pClient;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.jolokia.client.request.J4pSearchRequest;
import org.jolokia.client.request.J4pSearchResponse;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by andrew on 26/04/2014.
 */
@Service
public class JMXServiceImpl implements JMXService {
    @Autowired
    J4pClient j4pClient;

    @SuppressWarnings("unchecked")
    public JSONObject getRouteStates() {
        try {
            J4pSearchRequest j4pSearch = new J4pSearchRequest("org.apache.camel:type=routes,*");
            J4pSearchResponse j4psearchResponse = j4pClient.execute(j4pSearch);

            JSONObject result = new JSONObject();
            List<JSONObject> routes = new ArrayList<JSONObject>();

            for (String mbean : j4psearchResponse.getMBeanNames()) {
                J4pReadRequest readRequest = new J4pReadRequest(mbean);
                J4pReadResponse readResponse = j4pClient.execute(readRequest);
                routes.add((JSONObject) readResponse.asJSONObject().get("value"));
            }

            result.put("routes", routes);

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Something has gone wrong getting route statuses", e);
        }
    }

    public JSONObject getContextStats() {
        try {
            J4pSearchRequest j4pSearch = new J4pSearchRequest("org.apache.camel:type=context,*");
            J4pSearchResponse j4psearchResponse = j4pClient.execute(j4pSearch);

            List<String> mbeans = j4psearchResponse.getMBeanNames();

            J4pReadRequest readRequest = new J4pReadRequest(mbeans.get(0));
            J4pReadResponse readResponse = j4pClient.execute(readRequest);

            return (JSONObject) readResponse.asJSONObject().get("value");
        } catch (Exception e) {
            throw new RuntimeException("Problem getting context stats", e);
        }
    }
}
