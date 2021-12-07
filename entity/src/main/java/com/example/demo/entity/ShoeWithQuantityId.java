package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoeWithQuantityId implements Serializable {
    private BigInteger size;
    private ShoeWithQuantity.Color color;
}
