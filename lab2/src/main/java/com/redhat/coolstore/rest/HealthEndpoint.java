package com.redhat.coolstore.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/health")
public class HealthEndpoint {

    @GET
    @Path("/")
    public String check() {
        return "ok";
    }
}