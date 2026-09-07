package com.tidyup.StockService.domain.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateProductInventoryDTO(
        @NotNull
        @Positive
        Integer requestedAmount
) {
}
