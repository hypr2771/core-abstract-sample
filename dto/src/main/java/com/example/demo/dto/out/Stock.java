package com.example.demo.dto.out;

import com.example.demo.entity.Stock.State;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Size;
import java.util.List;

import static com.example.demo.entity.Stock.MAX_NUMBER_OF_SHOES;

@Value
@Builder
public class Stock {

    State state;
    @Size(max = MAX_NUMBER_OF_SHOES)
    List<ShoeWithQuantity> shoes;



}
