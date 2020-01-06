package com.decentralizer.spreadr.configuration;

import lombok.Data;

@Data
public class KafkaMessage {
    private String type;
    private Object payload;
}
