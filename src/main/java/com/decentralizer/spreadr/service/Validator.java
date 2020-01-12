package com.decentralizer.spreadr.service;

import com.decentralizer.spreadr.data.kafkaDTO.PaymentDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.TransporterDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.WarehuseDTOK;
import org.jvnet.hk2.annotations.Service;

@Service
public class Validator {

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
