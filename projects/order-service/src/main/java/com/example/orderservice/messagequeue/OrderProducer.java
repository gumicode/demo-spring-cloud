package com.example.orderservice.messagequeue;

import com.example.orderservice.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            new Field("string",true,"order_id"),
            new Field("string",true,"user_id"),
            new Field("int32",true,"quantity"),
            new Field("int32",true,"unit_price"),
            new Field("int32",true,"total_price")
    );

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders")
            .build();

    public OrderDto send(String topic, OrderDto oRderDto) {

        Payload payload = Payload.builder()
                .order_id(oRderDto.getOrderId())
                .product_id(oRderDto.getProductId())
                .quantity(oRderDto.getQuantity())
                .unit_price(oRderDto.getUnitPrice())
                .total_price(oRderDto.getTotalPrice())
                .build();

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(kafkaOrderDto);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonString);

        log.info("kafka Producer send data from Order MSA {}", kafkaOrderDto);

        return oRderDto;
    }
}
