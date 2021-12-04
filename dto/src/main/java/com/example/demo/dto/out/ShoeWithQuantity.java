package com.example.demo.dto.out;

import com.example.demo.entity.ShoeWithQuantity.Color;
import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;

@Value
@Builder
public class ShoeWithQuantity {

  BigInteger size;
  Color      color;
  BigInteger quantity;

}
