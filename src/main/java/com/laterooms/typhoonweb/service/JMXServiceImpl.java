package com.laterooms.typhoonweb.service;

import com.laterooms.typhoonweb.DTO.RouteStateDTO;
import org.springframework.stereotype.Service;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.*;

/**
 * Created by andrew on 26/04/2014.
 */
@Service
public class JMXServiceImpl implements JMXService {
    public List<RouteStateDTO> getRouteStates() throws RuntimeException {
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

            ObjectName name = new ObjectName("org.apache.camel:*");
            QueryExp query = Query.isInstanceOf(new StringValueExp("org.apache.camel.management.mbean.ManagedSuspendableRoute"));
            Set<ObjectInstance> queryResult = mBeanServer.queryMBeans(name, query);

            List<RouteStateDTO> states = new ArrayList<RouteStateDTO>();
            for (ObjectInstance obj : queryResult) {
                String endpoint = (String) mBeanServer.getAttribute(obj.getObjectName(), "EndpointUri");
                String state = (String) mBeanServer.getAttribute(obj.getObjectName(), "State");
                String route = (String) mBeanServer.getAttribute(obj.getObjectName(), "RouteId");

                states.add(new RouteStateDTO(route, endpoint, state));
            }

            return states;
        } catch (Exception e) {
            throw new RuntimeException("Something has gone wrong getting route statuses", e);
        }
    }
}
