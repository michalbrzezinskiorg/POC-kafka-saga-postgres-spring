package com.decentralizer.spreadr.data.kafkaDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTOK implements KafkaMessage {
    private String eventId;
    private String clientId;
    private Double totalPrice;
    private Integer quantity;
    private String itemId;
    private Boolean compensation;
}
