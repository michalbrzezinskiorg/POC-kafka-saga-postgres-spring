package com.decentralizer.spreadr.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class Sche {

    private final KafkaTemplate<String, String> template;
    private long counter;

    @Scheduled(fixedRate = 1000L)
    public void produce(){
        ListenableFuture<SendResult<String, String>> oo = template.send("counter", "data: "+ INSTANCE_ID + counter++);
        addCallback(oo);
    }

    private void addCallback(ListenableFuture<SendResult<String, String>> oo) {
        oo.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info(" sending ... [{}]", throwable);
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                log.info(" success ... produced [{}], \nmetadata [{}]", stringStringSendResult.getProducerRecord(), stringStringSendResult.getRecordMetadata());
            }
        });
    }

}
