package com.example.demo.dto.out;

import com.example.demo.dto.out.Shoe.ShoeBuilder;
import com.example.demo.dto.in.ShoeFilter.Color;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.math.BigInteger;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = ShoeBuilder.class)
public class Shoe {

  private String     name;
  private BigInteger size;
  private Color      color;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ShoeBuilder {

  }


}
