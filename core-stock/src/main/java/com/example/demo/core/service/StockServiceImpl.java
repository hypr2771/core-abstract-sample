package com.example.demo.core.service;

import com.example.demo.core.mapper.StockMapper;
import com.example.demo.core.mapper.StockMapperImpl;
import com.example.demo.core.repository.StockRepository;
import com.example.demo.core.repository.entity.StockEntity;
import com.example.demo.dto.common.StockShoe;
import com.example.demo.dto.out.Stock;
import com.example.demo.dto.out.Stock.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.dto.out.Stock.State.EMPTY;
import static com.example.demo.dto.out.Stock.State.FULL;
import static com.example.demo.dto.out.Stock.State.SOME;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    private StockMapper stockMapper = new StockMapperImpl();

    @Override
    public Stock getStock() {

        List<StockEntity> stock = stockRepository.findAll();

        List<StockShoe> stockShoes = stockMapper.toListStockShoes(stock);

        Integer totalStock = stockShoes.stream().mapToInt(value -> value.getQuantity()).sum();

        return Stock.builder()
                .shoes(stockShoes)
                .state(getState(totalStock))
                .build();
    }

    @Override
    public void patchStock(Stock stockDTO) {
        List<StockEntity> stock = stockMapper.toListStock(stockDTO.getShoes());

        Integer total = stock.stream().mapToInt(value -> value.getQuantity()).sum();

        if (total > 30){
            throw new IllegalArgumentException("The stock is greater than allowed");
        }

        stockRepository.deleteAll();

        stockRepository.saveAll(stock);
    }

    private State getState(Integer totalStock) {
        if (totalStock == 0){
            return EMPTY;
        }
        if (totalStock == 30){
            return FULL;
        }
        return SOME;
    }
}
