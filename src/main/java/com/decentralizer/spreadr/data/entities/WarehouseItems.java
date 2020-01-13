package com.decentralizer.spreadr.data.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Data
public class WarehouseItems implements Serializable {
    @Id
    @OneToOne
    private Product product;
    private Long quantity;
}
