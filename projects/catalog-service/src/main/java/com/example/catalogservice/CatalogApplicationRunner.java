package com.example.catalogservice;

import com.example.catalogservice.domain.CatalogEntity;
import com.example.catalogservice.domain.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CatalogApplicationRunner implements ApplicationRunner {

    private final CatalogRepository catalogRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        catalogRepository.save(CatalogEntity.builder().productId("CATALOG-001").productName("Berlin").stock(100).unitPrice(1500).build());
        catalogRepository.save(CatalogEntity.builder().productId("CATALOG-002").productName("Tokyo").stock(110).unitPrice(1000).build());
        catalogRepository.save(CatalogEntity.builder().productId("CATALOG-003").productName("Stockholm").stock(200).unitPrice(2000).build());
    }
}
