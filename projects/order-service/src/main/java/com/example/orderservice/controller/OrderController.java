package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final Environment environment;
    private final OrderService orderService;


    @GetMapping("/health_check")
    public String status(){
        return String.format("It`s Working in Order Service on PORT %s", environment.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable String userId, @RequestBody RequestOrder requestOrder) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = modelMapper.map(requestOrder, OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto createdOrderDto = orderService.createOrder(orderDto);

        ResponseOrder responseOrder = modelMapper.map(createdOrderDto, ResponseOrder.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);

    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable String userId) {

        Iterable<OrderEntity> orders = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> result = new ArrayList<>();

        orders.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });


        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
