package com.example.demo.dto.out;

import com.example.demo.dto.common.Color;
import com.example.demo.dto.out.Shoe.ShoeBuilder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = ShoeBuilder.class)
public class Shoe {

  String name;
  Integer size;
  Color color;

  @JsonPOJOBuilder(withPrefix = "")
  static class ShoeBuilder {

  }
}
