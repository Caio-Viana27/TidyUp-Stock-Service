package com.tidyup.StockService.controller;

import com.tidyup.StockService.Service.BrandService;
import com.tidyup.StockService.domain.product.dto.BrandDTO;
import com.tidyup.StockService.domain.product.dto.DetailedBrandDTO;
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
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    private final String URI_BASE = "http://localhost:8080/brands";

    @Transactional
    @PostMapping
    public ResponseEntity<DetailedBrandDTO> createBrand(@RequestBody @Valid BrandDTO brand, UriComponentsBuilder uriBuilder) {
        DetailedBrandDTO createdBrand = brandService.create(brand);
        URI location = uriBuilder.path(URI_BASE + "/{id}").buildAndExpand(createdBrand.id()).toUri();
        return ResponseEntity.created(location).body(createdBrand);
    }

    @GetMapping
    public ResponseEntity<Page<DetailedBrandDTO>> getBrands(@PageableDefault(size = 20) Pageable pageable) {
        Page<DetailedBrandDTO> page = brandService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedBrandDTO> getBrand(@PathVariable Long id) {
        DetailedBrandDTO brand = brandService.getById(id);
        return ResponseEntity.ok(brand);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<DetailedBrandDTO> updateBrand(@PathVariable Long id, @RequestBody @Valid DetailedBrandDTO detailedBrandDTO) {
        DetailedBrandDTO updatedBrand = brandService.update(id, detailedBrandDTO);
        return ResponseEntity.ok(updatedBrand);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity deleteBrand(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
