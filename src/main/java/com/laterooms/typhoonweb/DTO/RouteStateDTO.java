package com.laterooms.typhoonweb.DTO;

/**
 * Created by andrew on 26/04/2014.
 */
public class RouteStateDTO implements Comparable<RouteStateDTO> {
    private String state;
    private String routeId;
    private String endpointUri;

    public RouteStateDTO(String routeId, String endpointUri, String state) {
        this.state = state;
        this.routeId = routeId;
        this.endpointUri = endpointUri;
    }

    public String getState() {
        return state;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getEndpointUri() {
        return endpointUri;
    }

    public String toString() {
        return String.format("%s:%s:%s", routeId, state, endpointUri);
    }

    @Override
    public int compareTo(RouteStateDTO routeStateDTO) {
        int routeA = Integer.parseInt(getRouteId().substring(5));
        int routeB = Integer.parseInt(routeStateDTO.getRouteId().substring(5));
        return routeA - routeB;
    }
}
