package com.example.demo.dto.out;

import com.example.demo.dto.common.State;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.example.demo.dto.out.Stock.StockBuilder;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@JsonDeserialize(builder = StockBuilder.class)
public class Stock {

    State state;
    List<StockShoe> shoes;

    @JsonPOJOBuilder(withPrefix = "")
    static class StockBuilder {

    }
}
