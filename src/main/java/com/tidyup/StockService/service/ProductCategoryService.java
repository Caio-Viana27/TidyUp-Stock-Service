package com.tidyup.StockService.service;

import com.tidyup.StockService.repository.ProductCategoryRepository;
import com.tidyup.StockService.domain.product.dto.DetailedProductCategoryDTO;
import com.tidyup.StockService.domain.product.dto.ProductCategoryDTO;
import com.tidyup.StockService.domain.product.entity.ProductCategory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository categoryRepository;

    public DetailedProductCategoryDTO create(ProductCategoryDTO productCategory) {
        var categoryEntity = categoryRepository.save(new ProductCategory(productCategory));
        return new DetailedProductCategoryDTO(categoryEntity);
    }

    public Page<DetailedProductCategoryDTO> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(DetailedProductCategoryDTO::new);
    }

    public DetailedProductCategoryDTO getById(Long id) {
        return categoryRepository.findById(id).map(DetailedProductCategoryDTO::new).orElseThrow(EntityNotFoundException::new);
    }

    public DetailedProductCategoryDTO update(Long id, ProductCategoryDTO productCategory) {
        ProductCategory category = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        category.update(productCategory);
        return new DetailedProductCategoryDTO(category);
    }

    public void delete(Long id) {
        categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        categoryRepository.deleteById(id);
    }
}
