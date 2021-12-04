package com.example.demo.dto.out;

import com.example.demo.dto.out.Shoe.ShoeBuilder;
import com.example.demo.entity.ShoeWithQuantity.Color;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;

@Value
@Builder
@JsonDeserialize(builder = ShoeBuilder.class)
public class Shoe {

  String     name;
  BigInteger size;
  Color color;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ShoeBuilder {

  }


}
