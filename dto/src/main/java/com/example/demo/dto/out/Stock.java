package com.example.demo.dto.out;

import com.example.demo.dto.common.StockShoe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class Stock {

    State state;
    List<StockShoe> shoes;

    public enum State{
        EMPTY,
        FULL,
        SOME
    }
}
