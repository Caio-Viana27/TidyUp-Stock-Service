package com.tidyup.StockService.infrastructure.exception;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record InvalidInventoryMessage(
        @NotBlank
        String message,

        @NotNull
        UUID productId,

        @NotNull
        Integer currentInventory,

        @NotNull
        Integer requestedAmount
) {
    public InvalidInventoryMessage(InvalidNewProductInventoryValueException exception) {
        this(exception.getMessage(), exception.getProductId(), exception.getCurrentInventory(), exception.getRequestedAmount());
    }
}
