package com.example.demo.dto.out;

import com.example.demo.dto.common.StockShoe;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class StockDTO {

    State state;
    List<StockShoe> shoes;

    public enum State{
        EMPTY,
        FULL,
        SOME
    }
}
