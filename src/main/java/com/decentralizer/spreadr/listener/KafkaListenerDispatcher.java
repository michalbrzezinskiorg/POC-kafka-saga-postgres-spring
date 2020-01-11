package com.decentralizer.spreadr.listener;

import com.decentralizer.spreadr.data.kafkaDTO.OrderDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.PaymentDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.TransporterDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.WarehuseDTOK;
import com.decentralizer.spreadr.service.SagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.decentralizer.spreadr.SpreadrApplication.MAIN_TOPIC;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = MAIN_TOPIC, containerFactory = "kafkaListenerContainerFactory")
public class KafkaListenerDispatcher {

    private final SagaService sagaService;


    @KafkaHandler
    public void listenSagaDTOK(@Payload OrderDTOK message) {
        log.info("Received message in listenSagaDTOK: " + message);
        sagaService.handleOrderDTOK(message);
    }

    @KafkaHandler
    public void listenPaymentDTOK(PaymentDTOK message) {
        log.info("Received message in listenPaymentDTOK: " + message);
        sagaService.handlePaymentDTOK(message);
    }

    @KafkaHandler
    public void listenWarehuseDTOK(WarehuseDTOK message) {
        log.info("Received message in listenPaymentDTOK: " + message);
        sagaService.handleWarehuseDTOK(message);
    }

    @KafkaHandler
    public void listenTransporterDTOK(TransporterDTOK message) {
        log.info("Received message in listenPaymentDTOK: " + message);
        sagaService.handleTransporterDTOK(message);
    }
}
