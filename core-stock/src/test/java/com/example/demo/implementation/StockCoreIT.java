package com.example.demo.implementation;


import com.example.demo.TestApplicationConfiguration;
import com.example.demo.dto.common.State;
import com.example.demo.dto.out.StockState;
import com.example.demo.entity.Color;
import com.example.demo.entity.ShoeEntity;
import com.example.demo.mapper.StockMapper;
import com.example.demo.repository.StockShoeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.dto.common.Color.BLUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = TestApplicationConfiguration.class)
@ActiveProfiles("test")
@Transactional
class StockCoreIT {

    @Autowired
    private StockCore stockCore;

    @Autowired
    private StockShoeRepository stockShoeRepository;

    @Autowired
    private StockMapper stockMapper;

    @Test
    void getStockStateShouldReturnSomeIfStockIsNotFull() {

        // Given
        stockShoeRepository.save(
                ShoeEntity.builder()
                        .name("Kalenji")
                        .size(40)
                        .color(Color.BLUE)
                        .quantity(10)
                        .build());

        stockShoeRepository.save(
                ShoeEntity.builder()
                        .name("Quechua")
                        .size(40)
                        .color(Color.BLUE)
                        .quantity(10)
                        .build());

        // When
        StockState stockState = stockCore.getStockState();

        // Then
        assertEquals(stockState.getState(), State.SOME);
        assertEquals(stockState.getShoes()
                .get(0)
                .getColor(), BLUE);
        assertEquals(stockState.getShoes()
                .get(0)
                .getSize(), 40);
        assertEquals(stockState.getShoes()
                .get(0)
                .getQuantity(), 10);
        assertEquals(stockState.getShoes()
                .get(1)
                .getColor(), BLUE);
        assertEquals(stockState.getShoes()
                .get(1)
                .getSize(), 40);
        assertEquals(stockState.getShoes()
                .get(1)
                .getQuantity(), 10);
    }

    @Test
    void getStockStateShouldReturnEmptyIfStockIsEmpty() {
        // Given
        stockShoeRepository.save(
                ShoeEntity.builder()
                        .name("Kalenji")
                        .size(40)
                        .color(Color.BLUE)
                        .quantity(0)
                        .build());

        // When
        StockState stockState = stockCore.getStockState();

        // Then
        assertEquals(stockState.getState(), State.EMPTY);
        assertEquals(stockState.getShoes()
                .get(0)
                .getColor(), BLUE);
        assertEquals(stockState.getShoes()
                .get(0)
                .getSize(), 40);
        assertEquals(stockState.getShoes()
                .get(0)
                .getQuantity(), 0);
    }

    @Test
    void getStockStateShouldReturnEmptyIfNoShoeModelExists() {

        // When
        StockState stockState = stockCore.getStockState();

        // Then
        assertEquals(stockState.getState(), State.EMPTY);
        assertTrue(stockState.getShoes().isEmpty());
    }



    @Test
    void getStockStateShouldReturnFullIfStockIsFull() {
        // Given
        stockShoeRepository.save(
                ShoeEntity.builder()
                        .name("Kalenji")
                        .size(40)
                        .color(Color.BLUE)
                        .quantity(29)
                        .build());

        stockShoeRepository.save(
                ShoeEntity.builder()
                        .name("Quechua")
                        .size(40)
                        .color(Color.BLUE)
                        .quantity(1)
                        .build());


        // When
        StockState stockState = stockCore.getStockState();

        // Then
        assertEquals(stockState.getState(), State.FULL);
        assertEquals(stockState.getShoes()
                .get(0)
                .getColor(), BLUE);
        assertEquals(stockState.getShoes()
                .get(0)
                .getSize(), 40);
        assertEquals(stockState.getShoes()
                .get(0)
                .getQuantity(), 29);
        assertEquals(stockState.getShoes()
                .get(1)
                .getColor(), BLUE);
        assertEquals(stockState.getShoes()
                .get(1)
                .getSize(), 40);
        assertEquals(stockState.getShoes()
                .get(1)
                .getQuantity(), 1);
    }
}