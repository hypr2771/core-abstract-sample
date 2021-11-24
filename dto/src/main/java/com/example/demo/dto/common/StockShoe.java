package com.example.demo.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigInteger;

@Data
@Value
@Builder
@AllArgsConstructor
public class StockShoe{

    private String name;
    private BigInteger size;
    private String color;
    private Integer quantity;

}
