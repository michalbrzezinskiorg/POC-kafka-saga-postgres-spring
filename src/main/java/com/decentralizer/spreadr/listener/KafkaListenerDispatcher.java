package com.decentralizer.spreadr.listener;

import com.decentralizer.spreadr.data.kafkaDTO.*;
import com.decentralizer.spreadr.service.MorphService;
import com.decentralizer.spreadr.service.SagaOrchestrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.decentralizer.spreadr.service.MorphService.MAIN_TOPIC;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = MAIN_TOPIC, containerFactory = "kafkaListenerContainerFactory")
public class KafkaListenerDispatcher {

    private final MorphService morphService;
    private SagaOrchestrator sagaOrchestrator;

    @KafkaHandler
    public void listenMorphDTOK(MorphDTOK message) {
        log.info("Received message in listenMorphDTOK: " + message);
        morphService.saveToDb(message);
    }

    @KafkaHandler
    public void listenSagaDTOK(OrderDTOK message) {
        log.info("Received message in listenSagaDTOK: " + message);
        sagaOrchestrator.handleOrder(message);
    }

    @KafkaHandler
    public void listenPaymentDTOK(PaymentDTOK message) {
        log.info("Received message in listenPaymentDTOK: " + message);
        sagaOrchestrator.handleOrder(message);
    }

    @KafkaHandler
    public void listenWarehuseDTOK(WarehuseDTOK message) {
        log.info("Received message in listenPaymentDTOK: " + message);
        sagaOrchestrator.handleOrder(message);
    }

    @KafkaHandler
    public void listenTransporterDTOK(TransporterDTOK message) {
        log.info("Received message in listenPaymentDTOK: " + message);
        sagaOrchestrator.handleOrder(message);
    }
}
