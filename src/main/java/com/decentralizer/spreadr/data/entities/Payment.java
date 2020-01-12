package com.decentralizer.spreadr.data.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Warehouse warehouse;
    private BigDecimal amount;
    @OneToOne
    private Order order;
}
