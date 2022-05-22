package com.example.demo.dto.in;

import java.math.BigInteger;
import java.util.Optional;

import com.example.demo.dto.common.Color;
import lombok.Value;

@Value
public class ShoeFilter {

  Integer size;
  Color color;

  public Optional<Integer> getSize(){
    return Optional.ofNullable(size);
  }

  public Optional<Color> getColor(){
    return Optional.ofNullable(color);
  }

}
