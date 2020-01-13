package com.decentralizer.spreadr.saga;

import com.decentralizer.spreadr.data.entities.Warehouse;
import com.decentralizer.spreadr.data.kafkaDTO.*;
import com.decentralizer.spreadr.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.decentralizer.spreadr.service.MorphService.MAIN_TOPIC;

@Component
@RequiredArgsConstructor
@Slf4j
public class SagaOrchestrator {
    public static final String LACK_OF_VALIDATION = "lack of validation";
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private final Map<String, LocalDateTime> compensationsProcessed = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> paymentsAccepted = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> warehouseAccepted = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> transportAccepted = new ConcurrentHashMap<>();
    private final OrderService orderService;
    private final EventsValidator eventsValidator;

    public void sendInitOrderOnKafka(OrderDTOK orderDTOK) {
        kafkaTemplate.send(MAIN_TOPIC, orderDTOK);
    }

    public void handleOrder(OrderDTOK orderDTOK) {
        if (orderDTOK.getCompensation())
            handleCompensation(orderDTOK);
        else handle(orderDTOK, false);
    }

    public void handleOrder(PaymentDTOK paymentDTOK) {
        if (paymentDTOK.getCompensation()) {
            boolean shouldCompensate = paymentsAccepted.containsKey(paymentDTOK.getOrderDTOK().getEventId());
            orderService.handleFailoverPaymentDTOK(paymentDTOK, shouldCompensate);
        } else if (eventsValidator.validationForPayment(paymentDTOK)) {
            try {
                orderService.handleOperation(paymentDTOK);
                paymentsAccepted.put(paymentDTOK.getOrderDTOK().getEventId(), LocalDateTime.now());
            } catch (Exception e) {
                beginCompensation(paymentDTOK.getOrderDTOK(), e.getMessage(), PaymentDTOK.class);
            }
        } else beginCompensation(paymentDTOK.getOrderDTOK(), LACK_OF_VALIDATION, PaymentDTOK.class);
    }

    public void handleOrder(WarehuseDTOK warehuseDTOK) {
        if (warehuseDTOK.getCompensation()) {
            boolean shouldCompensate = warehouseAccepted.containsKey(warehuseDTOK.getOrderDTOK().getEventId());
            orderService.handleFailoverWarehuseDTOK(warehuseDTOK, shouldCompensate);
        } else if (eventsValidator.validationForWarehouse(warehuseDTOK)) {
            try {
                orderService.handleOperation(warehuseDTOK);
                warehouseAccepted.put(warehuseDTOK.getOrderDTOK().getEventId(), LocalDateTime.now());
            } catch (Exception e) {
                beginCompensation(warehuseDTOK.getOrderDTOK(), e.getMessage(), Warehouse.class);
            }
        } else beginCompensation(warehuseDTOK.getOrderDTOK(), LACK_OF_VALIDATION, Warehouse.class);
    }

    public void handleOrder(TransporterDTOK transporterDTOK) {
        if (transporterDTOK.getCompensation()) {
            boolean shouldCompensate = transportAccepted.containsKey(transporterDTOK.getOrderDTOK().getEventId());
            orderService.handleFailoverTransporterDTOK(transporterDTOK, shouldCompensate);
        } else if (eventsValidator.validationForTransport(transporterDTOK)) {
            try {
                orderService.handleOperation(transporterDTOK);
                transportAccepted.put(transporterDTOK.getOrderDTOK().getEventId(), LocalDateTime.now());
            } catch (Exception e) {
                beginCompensation(transporterDTOK.getOrderDTOK(), e.getMessage(), TransporterDTOK.class);
            }
        } else beginCompensation(transporterDTOK.getOrderDTOK(), LACK_OF_VALIDATION, TransporterDTOK.class);
    }

    public void handleCompensation(OrderDTOK orderDTOK) {
        if (shouldCompensateOrItWasAlreadyCompensated(orderDTOK.getEventId()))
            handle(orderDTOK, true);
    }

    private boolean shouldCompensateOrItWasAlreadyCompensated(String eventId) {
        boolean res = !compensationsProcessed.containsKey(eventId);
        if (!res)
            log.error("found {} duplicates of compensation process... should we handle it somehow?", compensationsProcessed.size());
        compensationsProcessed.put(eventId, LocalDateTime.now());
        return res;
    }

    private void handle(OrderDTOK orderDTOK, boolean b) {
        TransporterDTOK transporterDTOK = new TransporterDTOK(orderDTOK, b);
        WarehuseDTOK warehuseDTOK = new WarehuseDTOK(orderDTOK, b);
        PaymentDTOK paymentDTOK = new PaymentDTOK(orderDTOK, b);
        kafkaTemplate.send(MAIN_TOPIC, transporterDTOK);
        kafkaTemplate.send(MAIN_TOPIC, warehuseDTOK);
        kafkaTemplate.send(MAIN_TOPIC, paymentDTOK);
    }


    private void beginCompensation(OrderDTOK order, String cause, Class className) {
        log.error(cause + " [{}] ", className);
        kafkaTemplate.send(MAIN_TOPIC,
                OrderDTOK
                        .builder()
                        .totalPrice(order.getTotalPrice())
                        .clientId(order.getClientId())
                        .itemId(order.getItemId())
                        .eventId(order.getEventId())
                        .compensation(true)
                        .build());
    }
}
