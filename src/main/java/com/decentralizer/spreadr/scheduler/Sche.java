package com.decentralizer.spreadr.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class Sche {

    private final KafkaTemplate<String, String> template;
    private long counter;

    @Scheduled(fixedRate = 1000L)
    public void produce(){
        template.send("counter", "data: "+counter++);
    }

}
