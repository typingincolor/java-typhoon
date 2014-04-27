package com.laterooms.typhoonweb.service;

import com.laterooms.typhoonweb.DTO.RouteStateDTO;
import org.jolokia.client.J4pClient;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.jolokia.client.request.J4pSearchRequest;
import org.jolokia.client.request.J4pSearchResponse;
import org.springframework.stereotype.Service;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by andrew on 26/04/2014.
 */
@Service
public class JMXServiceImpl implements JMXService {
    public static void main(String args[]) {
        JMXServiceImpl impl = new JMXServiceImpl();
        for (RouteStateDTO route : impl.useJolokia()){
            System.out.println(route.toString());
        };
    }

    public List<RouteStateDTO> getRouteStates() throws RuntimeException {
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

            ObjectName name = new ObjectName("org.apache.camel:*");
            QueryExp query = Query.isInstanceOf(new StringValueExp("org.apache.camel.management.mbean.ManagedSuspendableRoute"));
            Set<ObjectInstance> queryResult = mBeanServer.queryMBeans(name, query);

            List<RouteStateDTO> states = new ArrayList<RouteStateDTO>();
            for (ObjectInstance obj : queryResult) {
                String[] attrs = {"RouteId", "EndpointUri", "State"};
                List<Attribute> result = mBeanServer.getAttributes(obj.getObjectName(), attrs).asList();

                states.add(new RouteStateDTO((String) result.get(0).getValue(), (String) result.get(1).getValue(), (String) result.get(2).getValue()));
            }

            return states;
        } catch (Exception e) {
            throw new RuntimeException("Something has gone wrong getting route statuses", e);
        }
    }

    public List<RouteStateDTO> useJolokia() {
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

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Something has gone wrong getting route statuses", e);
        }
    }
}
