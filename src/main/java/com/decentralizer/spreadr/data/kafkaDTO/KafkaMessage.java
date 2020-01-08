package com.decentralizer.spreadr.data.kafkaDTO;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
public class KafkaMessage<T> {
    private String type;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    private T payload;
}
