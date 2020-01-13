package com.decentralizer.spreadr.data.kafkaDTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class TransporterDTOK implements KafkaMessage {
    private OrderDTOK orderDTOK;
    private Boolean compensation;

    public TransporterDTOK(final OrderDTOK orderDTOK, final Boolean compensation) {
        this.orderDTOK = orderDTOK;
        this.compensation = compensation;
    }
}
