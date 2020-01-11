package com.decentralizer.spreadr.data.kafkaDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDTOK implements KafkaMessage {
    private OrderDTOK orderDTOK;
    private Boolean compensation;
    private String someAdditionalData;

    public PaymentDTOK(final OrderDTOK orderDTOK, final Boolean compensation, final String someAdditionalData) {
        this.orderDTOK = orderDTOK;
        this.compensation = compensation;
        this.someAdditionalData = someAdditionalData;
    }

}
