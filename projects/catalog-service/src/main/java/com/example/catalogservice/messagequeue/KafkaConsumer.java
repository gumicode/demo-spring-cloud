package com.example.catalogservice.messagequeue;

import com.example.catalogservice.domain.CatalogEntity;
import com.example.catalogservice.domain.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final CatalogRepository catalogRepository;

    @KafkaListener(topics = "example-catalog-topic")
    public void updateQuantity(String kafkaMessage) {

        log.info("kafka message {}", kafkaMessage);

        Map<String, Object> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            map = objectMapper.readValue(kafkaMessage, new TypeReference<>() {});
        } catch (JsonProcessingException ex) {
            ex.clearLocation();
        }

        CatalogEntity catalogEntity = catalogRepository.findByProductId((String)map.get("productId"));
        if(catalogEntity != null) {
            catalogEntity.setStock(  catalogEntity.getStock() - (Integer)map.get("quantity")  );
            catalogRepository.save(catalogEntity);
        } else {

            log.error("catalogEntity is null");
        }
    }
}
