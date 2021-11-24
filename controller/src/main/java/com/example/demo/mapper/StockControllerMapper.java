package com.example.demo.mapper;

import com.example.demo.controller.rdto.StockRDTO;
import com.example.demo.controller.rdto.StockShoeRDTO;
import com.example.demo.dto.common.StockShoe;
import com.example.demo.dto.out.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface StockControllerMapper {

    @Mapping(source = "state", target = "state", qualifiedByName = "toStateEnum")
    StockRDTO toStockRDTO (Stock stock);

    StockShoeRDTO toStockShoeRDTO (StockShoe stockShoe);

    @Mapping(source = "state", target = "state", qualifiedByName = "toState")
    Stock toStock (StockRDTO stockRDTO);

    StockShoe toStockShoe (StockShoeRDTO stockShoeRDTO);

    @Named("toStateEnum")
    default StockRDTO.StateEnum toStateEnum(Stock.State state) {
        return StockRDTO.StateEnum.valueOf(state.name());
    }

    @Named("toState")
    default Stock.State toState(StockRDTO.StateEnum stateEnum) {
        return stateEnum != null ? Stock.State.valueOf(stateEnum.name()) : null;
    }
}
