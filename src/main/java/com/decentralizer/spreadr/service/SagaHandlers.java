package com.decentralizer.spreadr.service;

import com.decentralizer.spreadr.data.entities.Payment;
import com.decentralizer.spreadr.data.entities.Transporter;
import com.decentralizer.spreadr.data.entities.Warehouse;
import com.decentralizer.spreadr.data.kafkaDTO.OrderDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.PaymentDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.TransporterDTOK;
import com.decentralizer.spreadr.data.kafkaDTO.WarehuseDTOK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SagaHandlers {

    Map<Integer, Transporter> transportersRepository = new ConcurrentHashMap<>();
    Map<Integer, Warehouse> warehouseRepository = new ConcurrentHashMap<>();
    Map<Integer, Payment> paymentRepository = new ConcurrentHashMap<>();

    @PostConstruct
    private void produced() {
        Warehouse w1 = new Warehouse(1, 100, 0, "apple");
        Warehouse w2 = new Warehouse(2, 100, 0, "oranges");
        Warehouse w3 = new Warehouse(3, 100, 0, "raspberry");
        warehouseRepository.put(1, w1);
        warehouseRepository.put(2, w2);
        warehouseRepository.put(3, w3);
    }

    void handleOperation(PaymentDTOK paymentDTOK) {
        log.info("handleOperation(PaymentDTOK transporterDTOK) [{}]", paymentDTOK);
        OrderDTOK orderDTOK = paymentDTOK.getOrderDTOK();
        Payment p = new Payment(
                paymentRepository.size(), orderDTOK.getClientId(), "us",
                orderDTOK.getPrice());
        paymentRepository.put(paymentRepository.size(), p);
        showTable(paymentRepository);
    }


    void handleOperation(WarehuseDTOK warehuseDTOK) {
        log.info("handleOperation(WarehuseDTOK warehuseDTOK) [{}]", warehuseDTOK);
        showTable(warehouseRepository);
        OrderDTOK o = warehuseDTOK.getOrderDTOK();
        Warehouse v = findWarehouse(o);
        v.setSold(v.getSold() + o.getQuantity());
        v.setWaitingItems(v.getWaitingItems() - o.getQuantity());
        showTable(warehouseRepository);
        if (v.getSold() < 0)
            throw new RuntimeException("out of stock");
    }

    public void handleOperation(TransporterDTOK transporterDTOK) {
        log.info("handleOperation(TransporterDTOK transporterDTOK) [{}]", transporterDTOK);
        showTable(transportersRepository);
        OrderDTOK o = transporterDTOK.getOrderDTOK();
        Transporter t = new Transporter(
                transportersRepository.size(), "spreadr", o.getClientId(), o.getItem(), o.getQuantity(), o.getEventId());
        transportersRepository.put(transportersRepository.size(), t);
        showTable(transportersRepository);
    }

    void handleFailoverTransporterDTOK(TransporterDTOK transporterDTOK) {
        log.error("handleFailoverTransporterDTOK(TransporterDTOK transporterDTOK) [{}]", transporterDTOK);
        showTable(transportersRepository);
        OrderDTOK o = transporterDTOK.getOrderDTOK();
        Optional<Map.Entry<Integer, Transporter>> orderToRemove = transportersRepository.entrySet().stream().filter(a -> a.getValue().getOrderId().equals(o.getEventId())).findFirst();
        transportersRepository.remove(orderToRemove.get().getKey());
        showTable(transportersRepository);
    }

    void handleFailoverWarehuseDTOK(WarehuseDTOK warehuseDTOK) {
        log.error("handleFailoverWarehuseDTOK(WarehuseDTOK warehuseDTOK) [{}]", warehuseDTOK);
        showTable(warehouseRepository);
        OrderDTOK o = warehuseDTOK.getOrderDTOK();
        Warehouse v = findWarehouse(o);
        v.setSold(v.getSold() - o.getQuantity());
        v.setWaitingItems(v.getWaitingItems() + o.getQuantity());
        showTable(warehouseRepository);
    }

    void handleFailoverPaymentDTOK(PaymentDTOK paymentDTOK) {
        log.error("handleFailoverPaymentDTOK(PaymentDTOK paymentDTOK) [{}]", paymentDTOK);
        showTable(paymentRepository);
        var o = paymentDTOK.getOrderDTOK();
        Payment p = new Payment(
                paymentRepository.size(), "us", paymentDTOK.getOrderDTOK().getClientId(),
                0 - o.getPrice());
        paymentRepository.put(paymentRepository.size(), p);
        showTable(paymentRepository);
    }


    private void showTable(Map repo) {
        //repo.forEach((a, b)-> log.info("key: [{}] value: [{}]", a, b));
    }

    private Warehouse findWarehouse(OrderDTOK o) {
        var first = warehouseRepository.entrySet().stream()
                .filter(k -> k.getValue().getItem()
                        .equals(o.getItem()))
                .findFirst();
        if (first.isEmpty()) return new Warehouse();
        return first.get().getValue();
    }
}
