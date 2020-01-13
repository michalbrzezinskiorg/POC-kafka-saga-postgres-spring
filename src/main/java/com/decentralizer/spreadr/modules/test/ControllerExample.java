package com.decentralizer.spreadr.modules.test;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationPath("/")
public class ControllerExample {

    @GET
    @Path("start")
    public String assdas() {
        return "asasas";
    }

}

