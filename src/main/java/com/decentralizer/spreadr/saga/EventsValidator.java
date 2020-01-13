package com.decentralizer.spreadr.saga;

import com.decentralizer.spreadr.data.kafkaDTO.PaymentDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.TransporterDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.WarehuseDTOK;
import org.springframework.stereotype.Service;

@Service
public class EventsValidator {

    boolean validationForTransport(TransporterDTOK transporterDTOK) {
        return transporterDTOK.hashCode() % 10 != 0;
    }

    boolean validationForWarehouse(WarehuseDTOK warehuseDTOK) {
        return warehuseDTOK.hashCode() % 13 != 0;
    }

    boolean validationForPayment(PaymentDTOK paymentDTOK) {
        return paymentDTOK.hashCode() % 7 != 0;
    }
}
