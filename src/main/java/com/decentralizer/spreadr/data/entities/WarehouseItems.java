package com.decentralizer.spreadr.data.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class WarehouseItems {
    @Id
    @OneToOne
    private Product product;
    private Long quantity;
}
