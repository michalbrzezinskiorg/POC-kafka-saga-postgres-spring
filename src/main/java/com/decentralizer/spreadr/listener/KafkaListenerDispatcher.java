package com.decentralizer.spreadr.listener;

import com.decentralizer.spreadr.data.kafkaDTO.KafkaMessage;
import com.decentralizer.spreadr.data.kafkaDTO.MorphDTOK;
import com.decentralizer.spreadr.service.MorphService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.decentralizer.spreadr.service.MorphService.MORPH_TOPIC;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = MORPH_TOPIC, containerFactory = "kafkaListenerContainerFactory")
public class KafkaListenerDispatcher {

    private final MorphService morphService;

    @KafkaHandler
    public void listen(KafkaMessage<MorphDTOK> message) {
        log.info("Received message in group morph: " + message);
        if (MORPH_TOPIC.equals(message.getType()))
            morphService.saveToDb(message.getPayload());
    }
}
