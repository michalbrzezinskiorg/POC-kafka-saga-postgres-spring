package com.decentralizer.spreadr.data.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String eventId;
    @ManyToOne
    private Client client;
    private BigDecimal totalPrice;
    @ManyToMany
    private List<OrderItem> orderItems;
    private Boolean compensation;
}
