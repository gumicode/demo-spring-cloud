package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final Environment environment;
    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;

    @GetMapping("/health_check")
    public String status(){
        return String.format("It`s Working in Order Service on PORT %s", environment.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable String userId, @RequestBody RequestOrder requestOrder) {

        log.info("Before add order data");

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = modelMapper.map(requestOrder, OrderDto.class);
        orderDto.setUserId(userId);

        // jpa
//        OrderDto createdOrderDto = orderService.createOrder(orderDto);
//        ResponseOrder responseOrder = modelMapper.map(createdOrderDto, ResponseOrder.class);

        // kafka
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQuantity() * orderDto.getUnitPrice());

        // send this order to kafka
        kafkaProducer.send("example-catalog-topic", orderDto);
        orderProducer.send("orders2", orderDto);

        ResponseOrder responseOrder = modelMapper.map(orderDto, ResponseOrder.class);

        log.info("Before added order data");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);

    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable String userId) {

        log.info("Before retrieve orders microservice");

        Iterable<OrderEntity> orders = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> result = new ArrayList<>();

        orders.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });

        log.info("After retrieve orders microservice");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
