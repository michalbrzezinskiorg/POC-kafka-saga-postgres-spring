package com.decentralizer.spreadr.data.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Warehouse from;
    @ManyToOne
    private Client to;
    @ManyToMany
    private List<OrderItem> orderItem;
    @OneToOne
    private Order order;
    private Boolean canceled;
}
