package com.example.demo.core.repository.entity;

import lombok.Value;

import java.math.BigInteger;

@Value
public class StockId {

    String name;
    BigInteger size;
    String color;

    public StockId(String name, BigInteger size, String color) {
        this.name = name;
        this.size = size;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public BigInteger getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }
}
