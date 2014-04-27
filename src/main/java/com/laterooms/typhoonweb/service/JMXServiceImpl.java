package com.laterooms.typhoonweb.service;

import com.laterooms.typhoonweb.DTO.RouteStateDTO;
import org.jolokia.client.J4pClient;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.jolokia.client.request.J4pSearchRequest;
import org.jolokia.client.request.J4pSearchResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by andrew on 26/04/2014.
 */
@Service
public class JMXServiceImpl implements JMXService {
    public List<RouteStateDTO> getRouteStates() {
        try {
            J4pClient j4pClient = new J4pClient("http://localhost:8080/typhoon/jolokia");
            J4pSearchRequest j4pSearch = new J4pSearchRequest("org.apache.camel:type=routes,*");
            J4pSearchResponse j4psearchResponse = j4pClient.execute(j4pSearch);

            List<RouteStateDTO> result = new ArrayList<RouteStateDTO>();

            for (String mbean : j4psearchResponse.getMBeanNames()) {
                J4pReadRequest readRequest = new J4pReadRequest(mbean);
                J4pReadResponse readResponse = j4pClient.execute(readRequest);

                RouteStateDTO routeStateDTO = new RouteStateDTO((String) readResponse.getValue("RouteId"), (String) readResponse.getValue("EndpointUri"), (String) readResponse.getValue("State"));

                result.add(routeStateDTO);
            }
            Collections.sort(result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Something has gone wrong getting route statuses", e);
        }
    }
}
