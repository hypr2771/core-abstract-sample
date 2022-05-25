package com.example.demo.dto.in;

import com.example.demo.dto.common.Color;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdatedStock {

    @NotBlank(message = "Name of the shoe is mandatory")
    String name;

    @NotNull
    @PositiveOrZero(message = "Size of the shoe cannot be less than 0")
    Integer size;

    @NotNull
    Color color;

    @NotNull
    @PositiveOrZero(message="Quantity of the shoe in the stock cannot be less than 0")
    @Max(value= 30, message = "Quantity of the shoe in the stock cannot be more than 30")
    Integer quantity;
}
