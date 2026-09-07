package com.tidyup.StockService.infrastructure.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class InvalidNewProductInventoryValueException extends RuntimeException {

    private final UUID productId;
    private final Integer currentInventory;
    private final Integer requestedAmount;

    public InvalidNewProductInventoryValueException(String message, UUID id, Integer inventory, Integer requestedAmount) {
        super(message);
        this.productId = id;
        this.currentInventory = inventory;
        this.requestedAmount = requestedAmount;
    }
}
