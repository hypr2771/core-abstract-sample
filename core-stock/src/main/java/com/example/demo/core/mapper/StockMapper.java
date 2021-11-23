package com.example.demo.core.mapper;

import com.example.demo.core.repository.model.Stock;
import com.example.demo.dto.common.StockShoe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface StockMapper {

    @Mapping(source = "stockId.name", target = "name")
    @Mapping(source = "stockId.size", target = "size")
    @Mapping(source = "stockId.color", target = "color")
    @Mapping(source = "quantity", target = "quantity")
    StockShoe toStockShoe (Stock stock);

    @Mapping(source = "name", target = "stockId.name")
    @Mapping(source = "size", target = "stockId.size")
    @Mapping(source = "color", target = "stockId.color")
    @Mapping(source = "quantity", target = "quantity")
    Stock toStock (StockShoe stockShoe);

    List<StockShoe> toListStockShoes(List<Stock> stock);

    List<Stock> toListStock(List<StockShoe> stockShoes);
}
