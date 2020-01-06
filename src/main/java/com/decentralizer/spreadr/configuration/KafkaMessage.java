package com.decentralizer.spreadr.configuration;

import com.decentralizer.spreadr.data.kafkaDTO.MorphDTOK;
import lombok.Data;

@Data
public class KafkaMessage {
    private String type;
    private MorphDTOK payload;
}
