package com.tidyup.StockService.service;

import com.tidyup.StockService.repository.ProductStatusRepository;
import com.tidyup.StockService.domain.product.dto.DetailedProductStatusDTO;
import com.tidyup.StockService.domain.product.dto.ProductStatusDTO;
import com.tidyup.StockService.domain.product.entity.ProductStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductStatusService {

    @Autowired
    private ProductStatusRepository productStatusRepository;

    public DetailedProductStatusDTO create(ProductStatusDTO dto) {
        ProductStatus productStatusEntity = productStatusRepository.save(new ProductStatus(dto));
        return new DetailedProductStatusDTO(productStatusEntity);
    }

    public Page<DetailedProductStatusDTO> getAll(Pageable pageable) {
        return productStatusRepository.findAll(pageable).map(DetailedProductStatusDTO::new);
    }

    public DetailedProductStatusDTO getById(Long id) {
        return productStatusRepository.findById(id).map(DetailedProductStatusDTO::new).orElseThrow(EntityNotFoundException::new);
    }

    public DetailedProductStatusDTO update(Long id, ProductStatusDTO dto) {
        ProductStatus productStatusEntity = productStatusRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        productStatusEntity.update(dto);
        return new DetailedProductStatusDTO(productStatusEntity);
    }

    public void delete(Long id) {
        productStatusRepository.deleteById(id);
    }
}
