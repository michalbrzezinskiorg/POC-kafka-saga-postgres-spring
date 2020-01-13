package com.decentralizer.spreadr.listener;

import com.decentralizer.spreadr.data.*;
import com.decentralizer.spreadr.data.kafkaDTO.KafkaMessage;
import com.decentralizer.spreadr.saga.EventsValidator;
import com.decentralizer.spreadr.saga.SagaOrchestrator;
import com.decentralizer.spreadr.service.OrderService;
import com.decentralizer.spreadr.service.PaymentService;
import com.decentralizer.spreadr.service.TransportService;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.mock;

class KafkaListenerDispatcherTest {

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate = mock(KafkaTemplate.class);
    private final WarehouseRepository warehouseRepository = mock(WarehouseRepository.class);
    private final ClientRepository clientRepository = mock(ClientRepository.class);
    private final OrderRepository orderRepository = mock(OrderRepository.class);
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final PaymentRepository paymentRepository = mock(PaymentRepository.class);
    private final PaymentService paymentService = new PaymentService(paymentRepository, warehouseRepository, clientRepository, orderRepository);
    private final TransportService transportService = new TransportService(warehouseRepository, clientRepository, orderRepository, productRepository);
    private final TransportRepository transportRepository = mock(TransportRepository.class);
    private final OrderService orderService = new OrderService(paymentService, transportService, transportRepository);
    private final EventsValidator eventsValidator = new EventsValidator();
    private final SagaOrchestrator sagaOrchestrator = new SagaOrchestrator(kafkaTemplate, orderService, eventsValidator);
    private KafkaListenerDispatcher kafkaListenerDispatcher = new KafkaListenerDispatcher(null, sagaOrchestrator);

    @Test
    void listenSagaDTOK() {
    }

    @Test
    void listenPaymentDTOK() {
    }

    @Test
    void listenWarehuseDTOK() {
    }

    @Test
    void listenTransporterDTOK() {
    }
}