package com.example.demo.exception;

public class MaxStockCapacityExceededException extends RuntimeException {

    public MaxStockCapacityExceededException(String message) {
        super(message);
    }
}
