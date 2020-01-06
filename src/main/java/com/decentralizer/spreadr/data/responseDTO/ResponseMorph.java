package com.decentralizer.spreadr.data.responseDTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResponseMorph {
    private Long id;
    @NotNull
    private String uuid;
    @NotNull
    private String name;
    private String from;
    private String to;
}
