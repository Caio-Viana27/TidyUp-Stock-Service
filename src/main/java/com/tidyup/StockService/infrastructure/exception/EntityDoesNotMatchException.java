package com.tidyup.StockService.infrastructure.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityDoesNotMatchException extends RuntimeException {

    public EntityDoesNotMatchException(String message) {
        super(message);
    }
}
