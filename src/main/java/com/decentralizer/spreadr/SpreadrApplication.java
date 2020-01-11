package com.decentralizer.spreadr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.UUID;

@SpringBootApplication
public class SpreadrApplication {

    public static final String INSTANCE_ID;
    public static final String MAIN_TOPIC = "morph";

    static {
        INSTANCE_ID = Base64.getEncoder()
                .encodeToString(
                        (System.getenv("INSTANCE")
                                + "_runtimeId:" + UUID.randomUUID()
                                + "_runtimeZonedDateTime:"
                                + ZonedDateTime.now().toString())
                        .getBytes()
                );
    }

    public static void main(String[] args) {
        SpringApplication.run(SpreadrApplication.class, args);
    }

}
