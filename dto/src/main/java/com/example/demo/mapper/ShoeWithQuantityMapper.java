package com.example.demo.mapper;

import com.example.demo.dto.out.ShoeWithQuantity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShoeWithQuantityMapper {
    ShoeWithQuantityMapper INSTANCE = Mappers.getMapper(ShoeWithQuantityMapper.class);

    ShoeWithQuantity shoeWithQuantityToDto(com.example.demo.entity.ShoeWithQuantity shoe);

    com.example.demo.entity.ShoeWithQuantity shoeWithQuantityToEntity(ShoeWithQuantity shoe);
}
