package com.decentralizer.spreadr.scheduler;

import com.decentralizer.spreadr.data.kafkaDTO.OrderDTOK;
import com.decentralizer.spreadr.service.SagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class SchedulerKafkaHeartbeat {
    private final SagaService sagaService;
    private long counter;


    @Scheduled(fixedDelay = 10_000L)
    public void produceSagaDTOK() {
        OrderDTOK orderDTOK = new OrderDTOK(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                (new Random().nextFloat() * 100) % 300,
                new Random().nextInt(10),
                Arrays.asList("orange", "apple", "raspberry").get((new Random().nextInt(3))),
                false);
        if (counter++ > 3) {
            try {
                sagaService.sendInitOrderOnKafka(orderDTOK);
            } catch (RuntimeException e) {
                log.error("InterruptedException {}", e.getMessage());
            }
        }
    }
}
