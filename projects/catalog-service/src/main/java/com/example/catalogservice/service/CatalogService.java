package com.example.catalogservice.service;

import com.example.catalogservice.domain.CatalogEntity;
import com.example.catalogservice.domain.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;

    @Transactional
    public Iterable<CatalogEntity> getAllCatalogs() {
        List<CatalogEntity> findAll =  catalogRepository.findAll();
        return findAll;
    }
}
