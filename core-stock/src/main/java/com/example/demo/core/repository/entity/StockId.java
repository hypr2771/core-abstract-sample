package com.example.demo.core.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigInteger;

@Builder
@Value
@AllArgsConstructor
@Data
public class StockId {

    String name;
    BigInteger size;
    String color;
}
