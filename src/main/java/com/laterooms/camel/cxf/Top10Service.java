package com.laterooms.camel.cxf;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Created by andrew on 19/05/2014.
 */
@Path("/top10")
public interface Top10Service {
    @GET
    @Path("/search")
    public String search(@QueryParam("id") List<String> id);
}
