package com.decentralizer.spreadr.service;

import com.decentralizer.spreadr.data.ClientRepository;
import com.decentralizer.spreadr.data.OrderRepository;
import com.decentralizer.spreadr.data.ProductRepository;
import com.decentralizer.spreadr.data.WarehouseRepository;
import com.decentralizer.spreadr.data.entities.*;
import com.decentralizer.spreadr.data.kafkaDTO.TransporterDTOK;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TransportService {

    private final WarehouseRepository warehouseRepository;
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    Order setAndReturnOrder(TransporterDTOK transporterDTOK, Transport transport, Client client) {
        Order order = orderRepository.findByEventId(transporterDTOK.getOrderDTOK().getEventId())
                .orElseGet(() -> {
                    Order o = new Order();
                    o.setClient(client);
                    o.setCompensation(transporterDTOK.getCompensation());
                    o.setEventId(transporterDTOK.getOrderDTOK().getEventId());
                    return o;
                });
        transport.setOrder(order);
        return order;
    }

    Warehouse setAndReturnWarehouse(Transport transport, Client client) {
        Warehouse warehouse = warehouseRepository.findByClient(client.getId())
                .orElse(warehouseRepository.save(
                        Warehouse.builder()
                                .clients(List.of(client))
                                .name(UUID.randomUUID().toString()).build()));
        transport.setFrom(warehouse);
        return warehouse;
    }

    Client setAndReturnClient(TransporterDTOK transporterDTOK, Transport transport) {
        Client client = clientRepository.findByName(transporterDTOK.getOrderDTOK().getClientId())
                .orElse(clientRepository.save(
                        Client.builder().name(transporterDTOK.getOrderDTOK().getClientId()).build()));
        transport.setTo(client);
        return client;
    }

    public Product setAndReturnItem(TransporterDTOK transporterDTOK, Transport transport) {
        Product product = productRepository.findByName(transporterDTOK.getOrderDTOK().getItemId()).orElseGet(() -> {
            Product p = new Product();
            p.setName(transporterDTOK.getOrderDTOK().getItemId());
            p.setPrice(BigDecimal.valueOf(new Random(40).nextInt()));
            return p;
        });
        OrderItem oi = new OrderItem();
        oi.setProduct(product);
        nullAsZero(transporterDTOK);
        oi.setQuantity(Long.valueOf(transporterDTOK.getOrderDTOK().getQuantity()));
        transport.setOrderItem(List.of(oi));
        return product;
    }

    private void nullAsZero(TransporterDTOK transporterDTOK) {
        transporterDTOK.getOrderDTOK().setQuantity(transporterDTOK.getOrderDTOK().getQuantity() == null ? 0 : transporterDTOK.getOrderDTOK().getQuantity());
    }
}
