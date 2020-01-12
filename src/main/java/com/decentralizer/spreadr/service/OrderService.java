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
import com.decentralizer.spreadr.data.kafkaDTO.TransporterDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.WarehuseDTOK;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final PaymentRepository paymentRepository;
    private final WarehouseRepository warehouseRepository;
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;

    void handleOperation(PaymentDTOK paymentDTOK) {
        log.info("handleOperation(PaymentDTOK paymentDTOK) [{}]", paymentDTOK);
        Payment payment = new Payment();
        OrderDTOK orderDTOK = paymentDTOK.getOrderDTOK();
        payment.setAmount(new BigDecimal(orderDTOK.getAmount()));
        Optional<Client> client = clientRepository.findByName(orderDTOK.getClientId());
        if (client.isEmpty())
            throw new RuntimeException("no such client");
        payment.setClient(client.get());
        Optional<Warehouse> warehouse = warehouseRepository.findByClient(client.get());
        if (warehouse.isEmpty())
            throw new RuntimeException("no such warehouse");
        payment.setWarehouse(warehouse.get());
        Order order = new Order();
        order.setClient(client.get());
        order.setCompensation(paymentDTOK.getCompensation());
        payment.setOrder(orderRepository.save(order));
        paymentRepository.save(payment);
    }

    void handleOperation(TransporterDTOK transporterDTOK) {
        log.info("handleOperation(TransporterDTOK transporterDTOK) [{}]", transporterDTOK);

    }

    void handleOperation(WarehuseDTOK warehuseDTOK) {
        log.info("handleOperation(WarehuseDTOK warehuseDTOK) [{}]", warehuseDTOK);

    }

    void handleFailoverTransporterDTOK(TransporterDTOK transporterDTOK) {
        log.error("handleFailoverTransporterDTOK(TransporterDTOK transporterDTOK) [{}]", transporterDTOK);
    }

    void handleFailoverWarehuseDTOK(WarehuseDTOK warehuseDTOK) {
        log.error("handleFailoverWarehuseDTOK(WarehuseDTOK warehuseDTOK) [{}]", warehuseDTOK);
    }

    void handleFailoverPaymentDTOK(PaymentDTOK paymentDTOK) {
        log.error("handleFailoverPaymentDTOK(PaymentDTOK paymentDTOK) [{}]", paymentDTOK);
    }

}
