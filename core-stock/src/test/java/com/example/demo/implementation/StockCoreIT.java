package com.example.demo.implementation;


import com.example.demo.TestApplicationConfiguration;
import com.example.demo.dto.common.State;
import com.example.demo.dto.in.UpdatedStock;
import com.example.demo.dto.out.StockShoe;
import com.example.demo.dto.out.StockState;
import com.example.demo.entity.Color;
import com.example.demo.entity.ShoeEntity;
import com.example.demo.exception.MaxStockCapacityExceededException;
import com.example.demo.mapper.StockMapper;
import com.example.demo.repository.StockShoeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.dto.common.Color.BLACK;
import static com.example.demo.dto.common.Color.BLUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertTrue(stockState.getShoes()
                .isEmpty());
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

    @Test
    void updateStockForExistingShoeShouldThrowExceptionIfStockMaxCapacityIsExceeded() {
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

        UpdatedStock updatedStock = UpdatedStock.builder()
                .name("Quechua")
                .color(BLUE)
                .size(40)
                .quantity(20)
                .build();

        // When -> Then
        assertThrows(MaxStockCapacityExceededException.class, () -> stockCore.updateStock(updatedStock));
    }


    @Test
    void updateStockForNewShoeShouldThrowExceptionIfStockMaxCapacityIsExceeded() {
        // Given
        stockShoeRepository.save(
                ShoeEntity.builder()
                        .name("Quechua")
                        .size(40)
                        .color(Color.BLUE)
                        .quantity(20)
                        .build());

        UpdatedStock updatedStock = UpdatedStock.builder()
                .name("Kalenji")
                .color(BLACK)
                .size(36)
                .quantity(20)
                .build();

        // When -> Then
        assertThrows(MaxStockCapacityExceededException.class, () -> stockCore.updateStock(updatedStock));
    }

    @Test
    void updateStockForExistingShoeShouldUpdateStock() {
        // Given
        stockShoeRepository.save(
                ShoeEntity.builder()
                        .name("Quechua")
                        .size(40)
                        .color(Color.BLUE)
                        .quantity(20)
                        .build());

        UpdatedStock updatedStock = UpdatedStock.builder()
                .name("Quechua")
                .color(BLUE)
                .size(40)
                .quantity(30)
                .build();

        // When
        StockShoe updatedShoeQuantity = stockCore.updateStock(updatedStock);

        // Then
        assertEquals(updatedShoeQuantity.getColor(), BLUE);
        assertEquals(updatedShoeQuantity.getSize(), 40);
        assertEquals(updatedShoeQuantity.getQuantity(), 30);
    }

    @Test
    void updateStockForNewShoeShouldUpdateStock() {
        // Given
        stockShoeRepository.save(
                ShoeEntity.builder()
                        .name("Quechua")
                        .size(40)
                        .color(Color.BLUE)
                        .quantity(20)
                        .build());

        UpdatedStock updatedStock = UpdatedStock.builder()
                .name("Kalenji")
                .color(BLUE)
                .size(42)
                .quantity(9)
                .build();

        // When
        StockShoe updatedShoeQuantity = stockCore.updateStock(updatedStock);

        // Then
        assertEquals(updatedShoeQuantity.getColor(), BLUE);
        assertEquals(updatedShoeQuantity.getSize(), 42);
        assertEquals(updatedShoeQuantity.getQuantity(), 9);
    }
}
