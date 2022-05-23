package com.example.demo.implementation;

import com.example.demo.core.AbstractStockCore;
import com.example.demo.core.Implementation;
import com.example.demo.dto.common.State;
import com.example.demo.dto.in.UpdatedStock;
import com.example.demo.dto.out.StockState;
import com.example.demo.dto.out.StockShoe;
import com.example.demo.mapper.StockMapper;
import com.example.demo.repository.StockShoeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Implementation(version = 1)
public class StockCore extends AbstractStockCore {

    private  static final int MAX_SHOES_QUANTITY = 30;
    private static final int MIN_SHOES_QUANTITY = 0;
    private final StockShoeRepository stockShoeRepository;
    private final StockMapper stockMapper;

    @Override
    public StockState getStockState() {
        List<StockShoe> stockShoes = stockShoeRepository.findAll()
                .stream()
                .map(stockMapper::shoeToStockShoeDTO)
                .collect(Collectors.toList());

        return StockState.builder().state(calculateStockState()).shoes(stockShoes).build();
    }

    /**
     * Calculate state of the stock
     */
    private State calculateStockState() {
        Integer currentQuantity = stockShoeRepository.getCurrentQuantityInStock().orElse(0);
        if(currentQuantity == MIN_SHOES_QUANTITY) {
            return State.EMPTY;
        } else if (currentQuantity < MAX_SHOES_QUANTITY) {
            return State.SOME;
        } else {
            return State.FULL;
        }
    }

    @Override
    public StockState updateStock(UpdatedStock updatedStock) {
        return null;
    }
}
