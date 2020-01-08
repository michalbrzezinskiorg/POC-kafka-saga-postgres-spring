package com.decentralizer.spreadr.data.kafkaDTO;

import lombok.Value;

@Value
public class PaymentDTOK {
    private OrderDTOK orderDTOK;
    private Boolean compensation;

    public PaymentDTOK(final OrderDTOK orderDTOK, final Boolean compensation) {
        this.orderDTOK = orderDTOK;
        this.compensation = compensation;
    }
}
