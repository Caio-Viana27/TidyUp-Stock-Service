package com.tidyup.StockService.controller;

import com.tidyup.StockService.service.ProductService;
import com.tidyup.StockService.domain.product.dto.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Value("${service.address}")
    private String URI_BASE;

    @Autowired
    private ProductService productService;

    @PostMapping
    @Transactional
    public ResponseEntity<ProductCreatedDTO> createProduct(@RequestBody @Valid CreateProductDTO productData, UriComponentsBuilder uriBuilder) {
        ProductCreatedDTO product = productService.create(productData);
        URI uri = uriBuilder.path(URI_BASE + "/products" + "/{id}").buildAndExpand(product.Id()).toUri();
        return ResponseEntity.created(uri).body(product);
    }

    @GetMapping
    public ResponseEntity<Page<ListProductDTO>> getProducts(@PageableDefault(size = 20) Pageable pageable) {
        Page<ListProductDTO> page = productService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListProductDTO> getProduct(@PathVariable UUID id) {
        ListProductDTO product = productService.getById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProductUpdatedDTO> updateProduct(@PathVariable UUID id, @RequestBody @Valid UpdateProductDTO data) {
        ProductUpdatedDTO product = productService.update(id, data);
        return ResponseEntity.ok(product);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ProductUpdatedDTO> updateProductStatus(@PathVariable UUID id, @RequestBody @Valid DetailedProductStatusDTO data) {
        ProductUpdatedDTO product = productService.update(id, data);
        return ResponseEntity.ok(product);
    }

    @PatchMapping("/{id}/categories")
    public ResponseEntity<ProductUpdatedDTO> updateProductStatus(@PathVariable UUID id, @RequestBody @Valid List<DetailedProductCategoryDTO> data) {
        ProductUpdatedDTO product = productService.update(id, data);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteProduct(@PathVariable UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
