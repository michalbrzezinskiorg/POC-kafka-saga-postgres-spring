package com.decentralizer.spreadr.service;

import com.decentralizer.spreadr.data.TransportRepository;
import com.decentralizer.spreadr.data.entities.Client;
import com.decentralizer.spreadr.data.entities.Payment;
import com.decentralizer.spreadr.data.entities.Transport;
import com.decentralizer.spreadr.data.kafkaDTO.PaymentDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.TransporterDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.WarehuseDTOK;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final PaymentService paymentService;
    private final TransportService transportService;
    private final TransportRepository transportRepository;

    public void handleOperation(PaymentDTOK paymentDTOK) {
        log.info("handleOperation(PaymentDTOK paymentDTOK) [{}]", paymentDTOK);
        Payment payment = new Payment();
        Client client = paymentService.setClientToPayment(payment, paymentDTOK.getOrderDTOK());
        paymentService.setWarehouseToPayment(payment, client);
        paymentService.createOrder(payment, client, paymentDTOK);
        payment.setAmount(new BigDecimal(paymentDTOK.getOrderDTOK().getTotalPrice()));
        paymentService.savePayment(payment);
    }

    public Transport handleOperation(TransporterDTOK transporterDTOK) {
        log.info("handleOperation(TransporterDTOK transporterDTOK) [{}]", transporterDTOK);
        Transport transport = new Transport();
        Client client = transportService.setAndReturnClient(transporterDTOK, transport);
        transportService.setAndReturnWarehouse(transport, client);
        transportService.setAndReturnOrder(transporterDTOK, transport, client);
        transportService.setAndReturnItem(transporterDTOK, transport);
        transport.setCanceled(transporterDTOK.getCompensation());
        return transportRepository.save(transport);
    }

    public void handleOperation(WarehuseDTOK warehuseDTOK) {
        log.info("handleOperation(WarehuseDTOK warehuseDTOK) [{}]", warehuseDTOK);

    }

    public void handleFailoverTransporterDTOK(TransporterDTOK transporterDTOK, boolean shouldCompensate) {
        if (!shouldCompensate) return;
        log.info("handleFailoverTransporterDTOK(TransporterDTOK transporterDTOK) [{}]", transporterDTOK);
        Transport transport = transportRepository.findByOrderId(transporterDTOK.getOrderDTOK().getEventId()).orElse(handleOperation(transporterDTOK));
        transport.setCanceled(transporterDTOK.getCompensation());
        transportRepository.save(transport);
    }

    public void handleFailoverWarehuseDTOK(WarehuseDTOK warehuseDTOK, boolean shouldCompensate) {
        if (!shouldCompensate) return;
        log.info("handleFailoverWarehuseDTOK(WarehuseDTOK warehuseDTOK) [{}]", warehuseDTOK);
    }

    public void handleFailoverPaymentDTOK(PaymentDTOK paymentDTOK, boolean shouldCompensate) {
        if (!shouldCompensate) return;
        log.info("handleFailoverPaymentDTOK(PaymentDTOK paymentDTOK) [{}]", paymentDTOK);
        Payment payment = new Payment();
        Client client = paymentService.setClientToPayment(payment, paymentDTOK.getOrderDTOK());
        paymentService.setWarehouseToPayment(payment, client);
        paymentService.createOrder(payment, client, paymentDTOK);
        payment.setAmount(new BigDecimal(paymentDTOK.getOrderDTOK().getTotalPrice()).multiply(BigDecimal.valueOf(-1L)));
        paymentService.savePayment(payment);
    }

}
