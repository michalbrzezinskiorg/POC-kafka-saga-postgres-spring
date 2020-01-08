package com.decentralizer.spreadr.scheduler;

import com.decentralizer.spreadr.data.kafkaDTO.KafkaMessage;
import com.decentralizer.spreadr.data.kafkaDTO.MorphDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.OrderDTOK;
import com.decentralizer.spreadr.service.MorphService;
import com.decentralizer.spreadr.service.SagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;
import static com.decentralizer.spreadr.service.SagaService.ORDER_TYPE;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class SchedulerKafkaHeartbeat {

    private final MorphService morphService;
    private SagaService sagaService;


    private long counter;

    @Scheduled(fixedDelay = 10_000L)
    public void produceMorphDTOK() {
        MorphDTOK morph = new MorphDTOK();
        morph.setName("TEST");
        morph.setFrom(INSTANCE_ID);
        morph.setTo(UUID.randomUUID().toString());
        morph.setUuid(UUID.randomUUID().toString());
        KafkaMessage<MorphDTOK> kafkaMessage = new KafkaMessage<>();
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

    @Scheduled(fixedDelay = 10_000L)
    public void produceSagaDTOK() {
        OrderDTOK orderDTOK = OrderDTOK.builder()
                .itemId("TEST")
                .clientId(INSTANCE_ID)
                .amount(String.valueOf(new Random().nextFloat()))
                .eventId(UUID.randomUUID().toString())
                .compensation(false)
                .build();
        KafkaMessage<OrderDTOK> kafkaMessage = new KafkaMessage<>();
        kafkaMessage.setPayload(orderDTOK);
        kafkaMessage.setType(ORDER_TYPE);
        if (counter++ > 3) {
            try {
                sagaService.sendInitOrderOnKafka(orderDTOK);
            } catch (RuntimeException e) {
                log.error("InterruptedException {}", e.getMessage());
            }
        }
    }
}
