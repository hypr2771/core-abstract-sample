package com.example.demo.dto.common;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigInteger;

@Data
@Value
@Builder
public class StockShoe{

    private String name;
    private BigInteger size;
    private String color;
    private Integer quantity;


}
