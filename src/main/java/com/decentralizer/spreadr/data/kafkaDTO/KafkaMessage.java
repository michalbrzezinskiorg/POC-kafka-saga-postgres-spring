package com.decentralizer.spreadr.data.kafkaDTO;

import lombok.Data;

@Data
public class KafkaMessage {
    private String type;
    private MorphDTOK payload;
}
