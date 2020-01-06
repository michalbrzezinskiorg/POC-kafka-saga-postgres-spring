package com.decentralizer.spreadr.listener;

import com.decentralizer.spreadr.configuration.KafkaMessage;
import com.decentralizer.spreadr.data.entities.Morph;
import com.decentralizer.spreadr.service.MorphService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import static com.decentralizer.spreadr.service.MorphService.MORPH_TOPIC;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerDispatcher {

    private final MorphService morphService;

    @KafkaListener(topics = MORPH_TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void listen(KafkaMessage message) {
        if (MORPH_TOPIC.equals(message.getType()))
            morphService.saveToDb((Morph) message.getPayload());
        System.out.println("Received message in group morph: " + message);
    }
}
