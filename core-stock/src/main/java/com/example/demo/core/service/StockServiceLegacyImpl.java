package com.example.demo.core.service;

import com.example.demo.core.mapper.StockMapper;
import com.example.demo.core.mapper.StockMapperImpl;
import com.example.demo.core.repository.StockRepository;
import com.example.demo.core.repository.model.Stock;
import com.example.demo.dto.common.StockShoe;
import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.out.StockDTO;
import com.example.demo.dto.out.StockDTO.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

import static com.example.demo.dto.out.StockDTO.State.EMPTY;
import static com.example.demo.dto.out.StockDTO.State.FULL;
import static com.example.demo.dto.out.StockDTO.State.SOME;

@Service
public class StockServiceLegacyImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    private StockMapper stockMapper = new StockMapperImpl();

    @Override
    public StockDTO getStock() {

        List<Stock> stock = stockRepository.findAll();

        List<StockShoe> stockShoes = stockMapper.toListStockShoes(stock);

        Integer totalStock = stockShoes.stream().mapToInt(value -> value.getQuantity()).sum();

        return StockDTO.builder()
                .shoes(stockShoes)
                .state(getState(totalStock))
                .build();
    }

    @Override
    public void patchStock(StockDTO stockDTO) {
        List<Stock> stock = stockMapper.toListStock(stockDTO.getShoes());

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
