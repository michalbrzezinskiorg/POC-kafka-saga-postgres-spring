package com.decentralizer.spreadr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@RequestMapping
public class ControllerExample {

    @GetMapping("aaaa")
    public String asdas(){
        return "asasas";
    }

    @GET
    @Path("start")
    public String assdas(){
        return "asasas";
    }


}

