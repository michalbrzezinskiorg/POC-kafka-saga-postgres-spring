package com.decentralizer.spreadr.listener;

import com.decentralizer.spreadr.data.kafkaDTO.*;
import com.decentralizer.spreadr.service.MorphService;
import com.decentralizer.spreadr.service.SagaService;
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
    private SagaService sagaService;

    @KafkaHandler
    public void listenMorphDTOK(KafkaMessage<MorphDTOK> message) {
        log.info("Received message in listenMorphDTOK: " + message);
        morphService.saveToDb(message.getPayload());
    }

    @KafkaHandler
    public void listenSagaDTOK(KafkaMessage<OrderDTOK> message) {
        log.info("Received message in listenSagaDTOK: " + message);
        sagaService.handleSaga(message.getPayload());
    }

    @KafkaHandler
    public void listenPaymentDTOK(KafkaMessage<PaymentDTOK> message) {
        log.info("Received message in listenPaymentDTOK: " + message);
        sagaService.handleSaga(message.getPayload());
    }

    @KafkaHandler
    public void listenWarehuseDTOK(KafkaMessage<WarehuseDTOK> message) {
        log.info("Received message in listenPaymentDTOK: " + message);
        sagaService.handleSaga(message.getPayload());
    }

    @KafkaHandler
    public void listenTransporterDTOK(KafkaMessage<TransporterDTOK> message) {
        log.info("Received message in listenPaymentDTOK: " + message);
        sagaService.handleSaga(message.getPayload());
    }
}
