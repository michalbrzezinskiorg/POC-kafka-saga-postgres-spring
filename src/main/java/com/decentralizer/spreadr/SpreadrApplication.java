package com.decentralizer.spreadr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZonedDateTime;
import java.util.UUID;

@SpringBootApplication
public class SpreadrApplication {

    public static final String INSTANCE_ID;

    static {
        INSTANCE_ID = System.getenv("INSTANCE") + "_runtimeId:" + UUID.randomUUID() + "_runtimeZonedDateTime:" + ZonedDateTime.now().toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpreadrApplication.class, args);
    }

}
