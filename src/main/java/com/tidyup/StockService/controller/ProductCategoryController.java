package com.tidyup.StockService.controller;

import com.tidyup.StockService.service.ProductCategoryService;
import com.tidyup.StockService.domain.product.dto.DetailedProductCategoryDTO;
import com.tidyup.StockService.domain.product.dto.ProductCategoryDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categories")
public class ProductCategoryController {

    private final String URI_BASE = "http://localhost:8080/categories";

    @Autowired
    private ProductCategoryService categoryService;

    @Transactional
    @PostMapping
    public ResponseEntity<DetailedProductCategoryDTO> createProductCategory(@RequestBody @Valid ProductCategoryDTO productCategory, UriComponentsBuilder uriBuilder) {
        DetailedProductCategoryDTO createdCategory = categoryService.create(productCategory);
        URI location = uriBuilder.path(URI_BASE + "/{id}").buildAndExpand(createdCategory.id()).toUri();
        return ResponseEntity.created(location).body(createdCategory);
    }

    @GetMapping
    public ResponseEntity<Page<DetailedProductCategoryDTO>> getProductCategories(@PageableDefault(size = 20) Pageable pageable) {
        Page<DetailedProductCategoryDTO> page = categoryService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedProductCategoryDTO> getProductCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @Transactional
    @PutMapping("/{id}")
    private ResponseEntity<DetailedProductCategoryDTO> updateProductCategory(@PathVariable Long id, @RequestBody ProductCategoryDTO productCategory) {
        DetailedProductCategoryDTO updatedCategory = categoryService.update(id, productCategory);
        return ResponseEntity.ok(updatedCategory);
    }

    @Transactional
    @DeleteMapping("/{id}")
    private ResponseEntity deleteProductCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}