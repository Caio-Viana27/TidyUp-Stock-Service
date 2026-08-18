package com.tidyup.StockService.domain.product.dto;

import com.tidyup.StockService.domain.product.entity.Brand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record DetailedBrandDTO(
        @NotNull
        Long id,

        @NotNull
        UUID retailerId,

        @NotBlank
        @Size(max = 50)
        String brand
) {
        public DetailedBrandDTO(Brand brand) {
                this(brand.getId(), brand.getRetailerId(), brand.getBrand());
        }
}
