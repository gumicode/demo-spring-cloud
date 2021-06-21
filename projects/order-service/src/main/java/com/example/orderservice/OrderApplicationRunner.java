package com.example.orderservice;

import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.jpa.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderApplicationRunner implements ApplicationRunner {

    private final OrderRepository orderRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setProductId("CATALOG-001");
        orderEntity.setQuantity(10);
        orderEntity.setUnitPrice(1500);
        orderEntity.setTotalPrice(1500 * 10);
        orderEntity.setUserId("test1");
        orderEntity.setOrderId("order1");
        orderRepository.save(orderEntity);

        OrderEntity orderEntity2 = new OrderEntity();
        orderEntity2.setProductId("CATALOG-002");
        orderEntity2.setQuantity(5);
        orderEntity2.setUnitPrice(1000);
        orderEntity2.setTotalPrice(1000 * 5);
        orderEntity2.setUserId("test1");
        orderEntity2.setOrderId("order2");
        orderRepository.save(orderEntity2);

        OrderEntity orderEntity3 = new OrderEntity();
        orderEntity3.setProductId("CATALOG-003");
        orderEntity3.setQuantity(6);
        orderEntity3.setUnitPrice(2000);
        orderEntity3.setTotalPrice(2000 * 6);
        orderEntity3.setUserId("test2");
        orderEntity3.setOrderId("order3");
        orderRepository.save(orderEntity3);

    }
}
