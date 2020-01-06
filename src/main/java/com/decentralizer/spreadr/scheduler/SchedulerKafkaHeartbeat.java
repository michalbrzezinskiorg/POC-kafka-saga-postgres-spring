package com.decentralizer.spreadr.scheduler;

import com.decentralizer.spreadr.configuration.KafkaMessage;
import com.decentralizer.spreadr.data.kafkaDTO.MorphDTOK;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class SchedulerKafkaHeartbeat {

    private final KafkaTemplate<String, KafkaMessage> template;
    private long counter;

    @Scheduled(fixedRate = 5000L)
    public void produce() {
        MorphDTOK morph = new MorphDTOK();
        morph.setName("TEST");
        morph.setFrom(INSTANCE_ID);
        morph.setTo(UUID.randomUUID().toString());
        morph.setUuid(UUID.randomUUID().toString());
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setPayload(morph);
        kafkaMessage.setType("morph");
        ListenableFuture<SendResult<String, KafkaMessage>> oo = template.send("counter", kafkaMessage);
        addCallback(oo);
    }

    private void addCallback(ListenableFuture<SendResult<String, KafkaMessage>> oo) {
        oo.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info(" sending ... [{}]", throwable);
            }

            @Override
            public void onSuccess(SendResult<String, KafkaMessage> stringStringSendResult) {
                log.info(" success ... produced [{}], \nmetadata [{}]", stringStringSendResult.getProducerRecord(), stringStringSendResult.getRecordMetadata());
            }
        });
    }
}
