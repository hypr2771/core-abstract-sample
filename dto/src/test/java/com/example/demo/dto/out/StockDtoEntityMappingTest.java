package com.example.demo.dto.out;

import com.example.demo.mapper.StockMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

class StockDtoEntityMappingTest {

    @Test
    void stockToEntityMapping() {
        ShoeWithQuantity shoe = ShoeWithQuantity.builder()
                .quantity(BigInteger.TWO)
                .size(BigInteger.ONE)
                .color(com.example.demo.entity.ShoeWithQuantity.Color.BLUE)
                .build();
        Stock dto = Stock.builder()
                .shoes(List.of(shoe))
                .state(com.example.demo.entity.Stock.State.SOME)
                .build();

        com.example.demo.entity.Stock entity = StockMapper.INSTANCE.stockToEntity(dto);
        Assertions.assertThat(entity.getShoes().get(0).getColor()).isEqualTo(dto.getShoes().get(0).getColor());
        Assertions.assertThat(entity.getShoes().get(0).getSize()).isEqualTo(dto.getShoes().get(0).getSize());
        Assertions.assertThat(entity.getShoes().get(0).getQuantity()).isEqualTo(dto.getShoes().get(0).getQuantity());
        Assertions.assertThat(entity.getState()).isEqualTo(dto.getState());
    }

    @Test
    void stockToDtoMapping() {
        com.example.demo.entity.ShoeWithQuantity shoe = com.example.demo.entity.ShoeWithQuantity.builder()
                .quantity(BigInteger.TWO)
                .size(BigInteger.ONE)
                .color(com.example.demo.entity.ShoeWithQuantity.Color.BLUE)
                .build();
        com.example.demo.entity.Stock entity = com.example.demo.entity.Stock.builder()
                .shoes(List.of(shoe))
                .state(com.example.demo.entity.Stock.State.SOME)
                .build();


        Stock dto = StockMapper.INSTANCE.stockToDto(entity);
        Assertions.assertThat(dto.getShoes().get(0).getColor()).isEqualTo(entity.getShoes().get(0).getColor());
        Assertions.assertThat(dto.getShoes().get(0).getSize()).isEqualTo(entity.getShoes().get(0).getSize());
        Assertions.assertThat(dto.getShoes().get(0).getQuantity()).isEqualTo(entity.getShoes().get(0).getQuantity());
        Assertions.assertThat(dto.getState()).isEqualTo(entity.getState());
    }

}