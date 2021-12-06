package com.example.demo.dto.out;

import com.example.demo.mapper.ShoeWithQuantityMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

class ShoeWithQuantityDtoEntityMappingTest {

    @Test
    void shoeWithQuantityToEntityMapping() {
        ShoeWithQuantity dto = ShoeWithQuantity.builder()
                .quantity(BigInteger.TWO)
                .size(BigInteger.ONE)
                .color(com.example.demo.entity.ShoeWithQuantity.Color.BLUE)
                .build();


        com.example.demo.entity.ShoeWithQuantity entity = ShoeWithQuantityMapper.INSTANCE.shoeWithQuantityToEntity(dto);
        Assertions.assertThat(entity.getQuantity()).isEqualTo(dto.getQuantity());
        Assertions.assertThat(entity.getSize()).isEqualTo(dto.getSize());
        Assertions.assertThat(entity.getColor()).isEqualTo(dto.getColor());
    }

    @Test
    void shoeWithQuantityToDtoMapping() {
        com.example.demo.entity.ShoeWithQuantity entity = com.example.demo.entity.ShoeWithQuantity.builder()
                .id(1)
                .quantity(BigInteger.TWO)
                .size(BigInteger.ONE)
                .color(com.example.demo.entity.ShoeWithQuantity.Color.BLUE)
                .build();


        ShoeWithQuantity dto = ShoeWithQuantityMapper.INSTANCE.shoeWithQuantityToDto(entity);
        Assertions.assertThat(dto.getQuantity()).isEqualTo(entity.getQuantity());
        Assertions.assertThat(dto.getSize()).isEqualTo(entity.getSize());
        Assertions.assertThat(dto.getColor()).isEqualTo(entity.getColor());
    }


}