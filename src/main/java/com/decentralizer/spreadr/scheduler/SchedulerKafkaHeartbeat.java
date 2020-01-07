package com.decentralizer.spreadr.scheduler;

import com.decentralizer.spreadr.data.kafkaDTO.KafkaMessage;
import com.decentralizer.spreadr.data.kafkaDTO.MorphDTOK;
import com.decentralizer.spreadr.service.MorphService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class SchedulerKafkaHeartbeat {

    private final MorphService morphService;
    private long counter;

    @Scheduled(fixedDelay = 10_000L)
    public void produce() {
        MorphDTOK morph = new MorphDTOK();
        morph.setName("TEST");
        morph.setFrom(INSTANCE_ID);
        morph.setTo(UUID.randomUUID().toString());
        morph.setUuid(UUID.randomUUID().toString());
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setPayload(morph);
        kafkaMessage.setType("morph");
        if (counter++ > 3) {
            try {
                morphService.sendOnKafka(morph);
            } catch (RuntimeException e) {
                log.error("InterruptedException {}", e.getMessage());
            }
        }
    }
}
