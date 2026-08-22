package com.tidyup.StockService.domain.product.dto;

import com.tidyup.StockService.domain.product.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProductCreatedDTO(
        @NotNull
        UUID Id,

        @NotNull
        UUID retailerId,

        @NotBlank @Size(max = 30)
        String SKU,

        @NotBlank @Size(max = 100)
        String name,

        @NotBlank @Size(max = 250)
        String description,

        @NotNull @PositiveOrZero
        BigDecimal price,

        @NotNull @PositiveOrZero
        Integer inventory,

        @NotNull
        DetailedProductStatusDTO status,

        @NotNull
        DetailedBrandDTO brand,

        @NotNull
        List<DetailedProductCategoryDTO> categoryList,

        @NotNull
        LocalDateTime createdAt
) {
    public ProductCreatedDTO(Product product) {
        this(product.getId(),
                product.getRetailerId(),
                product.getSKU(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getInventory(),
                new DetailedProductStatusDTO(product.getStatus()),
                new DetailedBrandDTO(product.getBrand()),
                product.getProductCategoryList().stream().map(DetailedProductCategoryDTO::new).toList(),
                product.getCreatedAt()
        );
    }
}
