package com.example.demo.dto.in;

import com.example.demo.entity.ShoeWithQuantity.Color;
import lombok.Value;

import java.math.BigInteger;
import java.util.Optional;

@Value
public class ShoeFilter {

  BigInteger size;
  Color color;



  public Optional<BigInteger> getSize(){
    return Optional.ofNullable(size);
  }

  public Optional<Color> getColor(){
    return Optional.ofNullable(color);
  }

}
