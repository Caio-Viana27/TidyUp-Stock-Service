package com.tidyup.StockService.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateProductDTO(
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
        List<DetailedProductCategoryDTO> categoriesList
) {
}
