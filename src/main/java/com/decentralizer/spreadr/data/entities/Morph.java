package com.decentralizer.spreadr.data.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Morph {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String uuid;
    private String name;
    @Column(name = "before")
    private String from;
    @Column(name = "after")
    private String to;

}
