package com.decentralizer.spreadr.data.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @OneToMany
    private List<WarehouseItems> warehouseItems;
    @OneToMany
    private List<Client> clients;
    @OneToMany
    private List<Transport> transports;
}
