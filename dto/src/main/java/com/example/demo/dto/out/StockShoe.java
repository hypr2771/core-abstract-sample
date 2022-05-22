package com.example.demo.dto.out;

import com.example.demo.dto.common.Color;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = StockShoe.StockShoeBuilder.class)
public class StockShoe {

    Color color;
    Integer size;
    Integer quantity;

    @JsonPOJOBuilder(withPrefix = "")
    static class StockShoeBuilder {

    }

}
