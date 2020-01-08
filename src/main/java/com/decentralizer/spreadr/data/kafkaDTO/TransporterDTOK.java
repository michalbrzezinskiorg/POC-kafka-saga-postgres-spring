package com.decentralizer.spreadr.data.kafkaDTO;

import lombok.Value;

@Value
public class TransporterDTOK {
    private OrderDTOK orderDTOK;
    private Boolean compensation;

    public TransporterDTOK(final OrderDTOK orderDTOK, final Boolean compensation) {
        this.orderDTOK = orderDTOK;
        this.compensation = compensation;
    }
}
