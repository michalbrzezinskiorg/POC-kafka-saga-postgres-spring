package com.decentralizer.spreadr.data.kafkaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTOK implements KafkaMessage {
    private String eventId;
    private String clientId;
    private Float price;
    private Integer quantity;
    private String item;
    private Boolean compensation;
}
