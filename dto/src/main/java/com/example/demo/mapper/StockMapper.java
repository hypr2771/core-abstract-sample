package com.example.demo.mapper;

import com.example.demo.dto.out.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StockMapper {
    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    Stock stockToDto(com.example.demo.entity.Stock stock);

    com.example.demo.entity.Stock stockToEntity(Stock stock);
}
