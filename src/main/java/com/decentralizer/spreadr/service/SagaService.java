package com.decentralizer.spreadr.service;

import com.decentralizer.spreadr.data.kafkaDTO.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.decentralizer.spreadr.service.MorphService.MAIN_TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class SagaService {

    public static final String ORDER_TYPE = "order";
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private final ModelMapper modelMapper;
    private final Map<String, LocalDateTime> compensations = new ConcurrentHashMap<>();

    public void sendInitOrderOnKafka(OrderDTOK orderDTOK) {
        kafkaTemplate.send(MAIN_TOPIC, new KafkaMessage<>(ORDER_TYPE, orderDTOK));
    }

    public void handleSaga(OrderDTOK orderDTOK) {
        if (orderDTOK.getCompensation()) handleCompensation(orderDTOK);
        else handle(orderDTOK, false);
    }

    public void handleCompensation(OrderDTOK orderDTOK) {
        if (shouldCompensate(orderDTOK.getEventId()))
            handle(orderDTOK, true);
    }

    public void handleSaga(PaymentDTOK paymentDTOK) {
        if (validationForPayment(paymentDTOK))
            handleOperation(paymentDTOK);

    }

    public void handleSaga(WarehuseDTOK warehuseDTOK) {
        if (validationForWarehouse(warehuseDTOK))
            handleOperation(warehuseDTOK);
    }

    public void handleSaga(TransporterDTOK transporterDTOK) {
        if (validationForTransport(transporterDTOK))
            handleOperation(transporterDTOK);
    }

    private void handleOperation(PaymentDTOK paymentDTOK) {
        log.info("handleOperation(PaymentDTOK paymentDTOK) [{}]", paymentDTOK);
    }

    private void handleOperation(TransporterDTOK transporterDTOK) {
        log.info("handleOperation(TransporterDTOK transporterDTOK) [{}]", transporterDTOK);

    }

    private void handleOperation(WarehuseDTOK warehuseDTOK) {
        log.info("handleOperation(WarehuseDTOK warehuseDTOK) [{}]", warehuseDTOK);

    }

    private void handleFailoverTransporterDTOK(TransporterDTOK transporterDTOK) {
        log.error("handleFailoverTransporterDTOK(TransporterDTOK transporterDTOK) [{}]", transporterDTOK);
    }

    private void handleFailoverWarehuseDTOK(WarehuseDTOK warehuseDTOK) {
        log.error("handleFailoverWarehuseDTOK(WarehuseDTOK warehuseDTOK) [{}]", warehuseDTOK);
    }

    private void handleFailoverPaymentDTOK(PaymentDTOK paymentDTOK) {
        log.error("handleFailoverPaymentDTOK(PaymentDTOK paymentDTOK) [{}]", paymentDTOK);
    }

    private boolean shouldCompensate(String eventId) {
        return !compensations.containsKey(eventId);
    }

    private void handle(OrderDTOK orderDTOK, boolean b) {
        TransporterDTOK transporterDTOK = new TransporterDTOK(orderDTOK, b);
        WarehuseDTOK warehuseDTOK = new WarehuseDTOK(orderDTOK, b);
        PaymentDTOK paymentDTOK = new PaymentDTOK(orderDTOK, b);
        kafkaTemplate.send(MAIN_TOPIC, new KafkaMessage<>("transport", transporterDTOK));
        kafkaTemplate.send(MAIN_TOPIC, new KafkaMessage<>("warehouse", warehuseDTOK));
        kafkaTemplate.send(MAIN_TOPIC, new KafkaMessage<>("payment", paymentDTOK));
    }


    private void beginCompensation(OrderDTOK order) {
        kafkaTemplate.send(MAIN_TOPIC, new KafkaMessage<>(
                        ORDER_TYPE,
                        OrderDTOK
                                .builder()
                                .amount(order.getAmount())
                                .clientId(order.getClientId())
                                .itemId(order.getItemId())
                                .eventId(order.getEventId())
                                .compensation(true)
                                .build()
                )
        );
    }

    private boolean validationForTransport(TransporterDTOK transporterDTOK) {
        OrderDTOK order = transporterDTOK.getOrderDTOK();
        if (transporterDTOK.hashCode() % 10 == 0) {
            beginCompensation(order);
            return false;
        }
        if (transporterDTOK.getCompensation()) {
            handleFailoverTransporterDTOK(transporterDTOK);
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
            handleFailoverWarehuseDTOK(warehuseDTOK);
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
            handleFailoverPaymentDTOK(paymentDTOK);
            return false;
        }
        return true;
    }
}
