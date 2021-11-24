package com.example.demo.core.service;

import com.example.demo.core.mapper.StockServiceMapper;
import com.example.demo.core.repository.StockRepository;
import com.example.demo.dto.out.Stock;
import com.example.demo.dto.out.Stock.State;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.dto.out.Stock.State.EMPTY;
import static com.example.demo.dto.out.Stock.State.FULL;
import static com.example.demo.dto.out.Stock.State.SOME;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockServiceMapper stockServiceMapper;

    @Override
    public Stock getStock() {

        val stock = stockRepository.findAll();

        val stockShoes = stockServiceMapper.toListStockShoes(stock);

        val totalStock = stockShoes.stream().mapToInt(value -> value.getQuantity()).sum();

        return Stock.builder()
                .shoes(stockShoes)
                .state(getState(totalStock))
                .build();
    }

    @Override
    public void updateStock(Stock stockDTO) {
        val stock = stockServiceMapper.toListStock(stockDTO.getShoes());

        val total = stock.stream().mapToInt(value -> value.getQuantity()).sum();

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
