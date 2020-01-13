package com.decentralizer.spreadr.data.kafkaDTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class PaymentDTOK implements KafkaMessage {
    private OrderDTOK orderDTOK;
    private Boolean compensation;

    public PaymentDTOK(final OrderDTOK orderDTOK, final Boolean compensation) {
        this.orderDTOK = orderDTOK;
        this.compensation = compensation;
    }
}
