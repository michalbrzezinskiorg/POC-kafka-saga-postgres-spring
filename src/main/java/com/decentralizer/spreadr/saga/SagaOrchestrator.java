package com.decentralizer.spreadr.saga;

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
    private final Map<String, LocalDateTime> compensations = new ConcurrentHashMap<>();
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
        if (paymentDTOK.getCompensation())
            orderService.handleFailoverPaymentDTOK(paymentDTOK);
        else if (eventsValidator.validationForPayment(paymentDTOK)) {
            orderService.handleOperation(paymentDTOK);
        } else beginCompensation(paymentDTOK.getOrderDTOK(), LACK_OF_VALIDATION);
    }

    public void handleOrder(WarehuseDTOK warehuseDTOK) {
        if (warehuseDTOK.getCompensation())
            orderService.handleFailoverWarehuseDTOK(warehuseDTOK);
        else if (eventsValidator.validationForWarehouse(warehuseDTOK)) {
            orderService.handleOperation(warehuseDTOK);
        } else beginCompensation(warehuseDTOK.getOrderDTOK(), LACK_OF_VALIDATION);
    }

    public void handleOrder(TransporterDTOK transporterDTOK) {
        if (transporterDTOK.getCompensation())
            orderService.handleFailoverTransporterDTOK(transporterDTOK);
        else if (eventsValidator.validationForTransport(transporterDTOK)) {
            orderService.handleOperation(transporterDTOK);
        } else beginCompensation(transporterDTOK.getOrderDTOK(), LACK_OF_VALIDATION);
    }

    public void handleCompensation(OrderDTOK orderDTOK) {
        if (shouldCompensateOrItWasAlreadyCompensated(orderDTOK.getEventId()))
            handle(orderDTOK, true);
    }

    private boolean shouldCompensateOrItWasAlreadyCompensated(String eventId) {
        boolean res = !compensations.containsKey(eventId);
        if (!res)
            log.error("found {} duplicates of compensation process... should we handle it somehow?", compensations.size());
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


    private void beginCompensation(OrderDTOK order, String cause) {
        log.error(cause);
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
