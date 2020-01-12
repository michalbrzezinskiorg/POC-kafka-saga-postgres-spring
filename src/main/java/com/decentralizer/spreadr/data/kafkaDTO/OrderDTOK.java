package com.decentralizer.spreadr.data.kafkaDTO;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderDTOK implements KafkaMessage {
    private String eventId;
    private String clientId;
    private String amount;
    private String itemId;
    private Boolean compensation;
}
