package com.decentralizer.spreadr.service;

import com.decentralizer.spreadr.data.ClientRepository;
import com.decentralizer.spreadr.data.OrderRepository;
import com.decentralizer.spreadr.data.PaymentRepository;
import com.decentralizer.spreadr.data.WarehouseRepository;
import com.decentralizer.spreadr.data.entities.Client;
import com.decentralizer.spreadr.data.entities.Order;
import com.decentralizer.spreadr.data.entities.Payment;
import com.decentralizer.spreadr.data.entities.Warehouse;
import com.decentralizer.spreadr.data.kafkaDTO.OrderDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.PaymentDTOK;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final WarehouseRepository warehouseRepository;
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;

    Client setClientToPayment(Payment payment, OrderDTOK orderDTOK) {
        log.info("setClientToPayment(Payment [{}], OrderDTOK [{}])", payment, orderDTOK);
        Client client = getOrCreateClient(orderDTOK);
        payment.setClient(client);
        return client;
    }

    Client getOrCreateClient(final OrderDTOK orderDTOK) {
        Optional<Client> clientOpt = clientRepository.findByName(orderDTOK.getClientId());
        return clientOpt.orElse(clientRepository.save(Client.builder().name(orderDTOK.getClientId()).build()));
    }

    void createOrder(final Payment payment, Client client, final PaymentDTOK paymentDTOK) {
        Order order = orderRepository.findByEventId(paymentDTOK.getOrderDTOK().getEventId())
                .orElseGet(() -> {
                    Order o = new Order();
                    o.setClient(client);
                    o.setCompensation(paymentDTOK.getCompensation());
                    o.setEventId(paymentDTOK.getOrderDTOK().getEventId());
                    return o;
                });
        payment.setOrder(orderRepository.save(order));
    }

    Warehouse setWarehouseToPayment(final Payment payment, final Client client) {
        log.info("setWarehouseToPayment(final Payment [{}], final Client [{}]) ", payment, client);
        Warehouse warehouse = warehouseRepository.findByClient(client.getId())
                .orElse(warehouseRepository.save(Warehouse.builder()
                        .clients(List.of(client))
                        .name(UUID.randomUUID().toString()).build()));
        payment.setWarehouse(warehouse);
        return warehouse;
    }

    void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }
}