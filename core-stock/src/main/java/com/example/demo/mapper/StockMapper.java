package com.example.demo.mapper;

import com.example.demo.dto.in.UpdatedStock;
import com.example.demo.dto.out.StockShoe;
import com.example.demo.entity.ShoeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {
    StockShoe shoeToStockShoeDTO(ShoeEntity shoeEntity);
    ShoeEntity updatedStockToShoeEntity(UpdatedStock updatedStock);
}
