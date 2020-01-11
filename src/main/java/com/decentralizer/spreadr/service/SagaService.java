package com.decentralizer.spreadr.service;

import com.decentralizer.spreadr.data.kafkaDTO.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.decentralizer.spreadr.SpreadrApplication.MAIN_TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class SagaService {

    public static final String ORDER_TYPE = "order";
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private final SagaHandlers handlers;
    private final Map<String, LocalDateTime> compensations = new ConcurrentHashMap<>();

    public void sendInitOrderOnKafka(OrderDTOK orderDTOK) {
        kafkaTemplate.send(MAIN_TOPIC, orderDTOK);
    }

    public void handleOrderDTOK(OrderDTOK orderDTOK) {
        if (orderDTOK.getCompensation()) handleCompensation(orderDTOK);
        else handle(orderDTOK, false);
    }

    public void handleCompensation(OrderDTOK orderDTOK) {
        if (shouldCompensate(orderDTOK.getEventId()))
            handle(orderDTOK, true);
    }

    public void handlePaymentDTOK(PaymentDTOK paymentDTOK) {
        if (validationForPayment(paymentDTOK))
            handlers.handleOperation(paymentDTOK);

    }

    public void handleWarehuseDTOK(WarehuseDTOK warehuseDTOK) {
        if (validationForWarehouse(warehuseDTOK))
            handlers.handleOperation(warehuseDTOK);
    }

    public void handleTransporterDTOK(TransporterDTOK transporterDTOK) {
        if (validationForTransport(transporterDTOK))
            handlers.handleOperation(transporterDTOK);
    }

    private boolean shouldCompensate(String eventId) {
        boolean res = !compensations.containsKey(eventId);
        if (!res)
            log.error("found {} duplicates of compensation process... should we handle it somehow?", compensations.size());
        return res;
    }

    private void handle(OrderDTOK orderDTOK, boolean b) {
        TransporterDTOK transporterDTOK = new TransporterDTOK(orderDTOK, b, getSomeAdditionalData());
        WarehuseDTOK warehuseDTOK = new WarehuseDTOK(orderDTOK, b, getSomeAdditionalData());
        PaymentDTOK paymentDTOK = new PaymentDTOK(orderDTOK, b, getSomeAdditionalData());
        kafkaTemplate.send(MAIN_TOPIC, transporterDTOK);
        kafkaTemplate.send(MAIN_TOPIC, warehuseDTOK);
        kafkaTemplate.send(MAIN_TOPIC, paymentDTOK);
    }

    private String getSomeAdditionalData() {
        return "something additional";
    }


    private void beginCompensation(OrderDTOK order) {
        kafkaTemplate.send(MAIN_TOPIC,
                new OrderDTOK(
                        order.getEventId(),
                        order.getClientId(),
                        order.getPrice(),
                        order.getQuantity(),
                        order.getItem(),
                        true));
    }

    private boolean validationForTransport(TransporterDTOK transporterDTOK) {
        OrderDTOK order = transporterDTOK.getOrderDTOK();
        if (transporterDTOK.hashCode() % 10 == 0) {
            beginCompensation(order);
            return false;
        }
        if (transporterDTOK.getCompensation()) {
            handlers.handleFailoverTransporterDTOK(transporterDTOK);
            return false;
        }
        return true;
    }

    private boolean validationForWarehouse(WarehuseDTOK warehuseDTOK) {
        OrderDTOK order = warehuseDTOK.getOrderDTOK();
        if (warehuseDTOK.hashCode() % 13 == 0) {
            beginCompensation(order);
            return false;
        }
        if (warehuseDTOK.getCompensation()) {
            handlers.handleFailoverWarehuseDTOK(warehuseDTOK);
            return false;
        }
        return true;
    }

    private boolean validationForPayment(PaymentDTOK paymentDTOK) {
        OrderDTOK order = paymentDTOK.getOrderDTOK();
        if (paymentDTOK.hashCode() % 7 == 0) {
            beginCompensation(order);
            return false;
        }
        if (paymentDTOK.getCompensation()) {
            handlers.handleFailoverPaymentDTOK(paymentDTOK);
            return false;
        }
        return true;
    }
}
