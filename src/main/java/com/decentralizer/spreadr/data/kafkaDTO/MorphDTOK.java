package com.decentralizer.spreadr.data.kafkaDTO;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class MorphDTOK implements KafkaMessage {
    private Long id;
    @NotNull
    private String uuid;
    @NotNull
    private String name;
    @Length(message = "needs to between 10 and 100", min = 10, max = 100)
    private String from;
    @Length(message = "needs to between 10 and 100", min = 10, max = 100)
    private String to;

    @Override
    public Boolean getCompensation() {
        return false;
    }
}
