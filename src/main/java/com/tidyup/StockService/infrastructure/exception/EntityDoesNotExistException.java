package com.tidyup.StockService.infrastructure.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityDoesNotExistException extends RuntimeException {

    public EntityDoesNotExistException(String message) {
        super(message);
    }
}
