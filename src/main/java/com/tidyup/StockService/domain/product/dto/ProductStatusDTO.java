package com.tidyup.StockService.domain.product.dto;

import com.tidyup.StockService.domain.product.entity.ProductStatus;
import com.tidyup.StockService.domain.product.entity.StatusValue;
import jakarta.validation.constraints.NotNull;

public record ProductStatusDTO(
        @NotNull
        StatusValue status
) {
    public ProductStatusDTO(ProductStatus productStatus) {
        this(productStatus.getStatus());
    }
}
