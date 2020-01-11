package com.decentralizer.spreadr.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transporter {
    private Integer id;
    private String from;
    private String to;
    private String items;
    private Integer quantity;
    private String orderId;
}
