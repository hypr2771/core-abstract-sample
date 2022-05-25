package com.example.demo.implementation;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.demo.dto.common.Color.BLACK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockCoreTest {

    @InjectMocks
    private StockCore stockCore;

    @Mock
    private StockShoeRepository stockShoeRepository;

    @Mock
    private StockMapper stockMapper;

    @Test
    void getStockStateShouldReturnSomeIfStockIsNotFull() {
        // Given
        ShoeEntity shoeEntity = ShoeEntity.builder()
                .name("Kalenji")
                .color(Color.BLACK)
                .size(40)
                .quantity(10)
                .build();
        List<ShoeEntity> shoes = Collections.singletonList(shoeEntity);
        when(stockShoeRepository.getCurrentQuantityInStock()).thenReturn(Optional.of(10));
        when(stockShoeRepository.findAll()).thenReturn(shoes);
        when(stockMapper.shoeToStockShoeDTO(shoeEntity)).thenReturn(StockShoe.builder()
                .color(BLACK)
                .size(40)
                .quantity(10)
                .build());

        // When
        StockState stockState = stockCore.getStockState();

        // Then
        assertEquals(stockState.getState(), State.SOME);
        assertEquals(stockState.getShoes()
                .get(0)
                .getColor(), BLACK);
        assertEquals(stockState.getShoes()
                .get(0)
                .getSize(), 40);
        assertEquals(stockState.getShoes()
                .get(0)
                .getQuantity(), 10);
    }

    @Test
    void getStockStateShouldReturnEmptyIfStockIsEmpty() {
        // Given
        ShoeEntity shoeEntity = ShoeEntity.builder()
                .name("Kalenji")
                .color(Color.BLACK)
                .size(40)
                .quantity(0)
                .build();
        List<ShoeEntity> shoes = Collections.singletonList(shoeEntity);
        when(stockShoeRepository.getCurrentQuantityInStock()).thenReturn(Optional.of(0));
        when(stockShoeRepository.findAll()).thenReturn(shoes);
        when(stockMapper.shoeToStockShoeDTO(shoeEntity)).thenReturn(StockShoe.builder()
                .color(BLACK)
                .size(40)
                .quantity(0)
                .build());

        // When
        StockState stockState = stockCore.getStockState();

        // Then
        assertEquals(stockState.getState(), State.EMPTY);
        assertEquals(stockState.getShoes()
                .get(0)
                .getColor(), BLACK);
        assertEquals(stockState.getShoes()
                .get(0)
                .getSize(), 40);
        assertEquals(stockState.getShoes()
                .get(0)
                .getQuantity(), 0);
    }


    @Test
    void getStockStateShouldReturnFullIfStockIsFull() {
        // Given
        ShoeEntity shoeEntity = ShoeEntity.builder()
                .name("Kalenji")
                .color(Color.BLACK)
                .size(40)
                .quantity(30)
                .build();
        List<ShoeEntity> shoes = Collections.singletonList(shoeEntity);
        when(stockShoeRepository.getCurrentQuantityInStock()).thenReturn(Optional.of(30));
        when(stockShoeRepository.findAll()).thenReturn(shoes);
        when(stockMapper.shoeToStockShoeDTO(shoeEntity)).thenReturn(StockShoe.builder()
                .color(BLACK)
                .size(40)
                .quantity(30)
                .build());

        // When
        StockState stockState = stockCore.getStockState();

        // Then
        assertEquals(stockState.getState(), State.FULL);
        assertEquals(stockState.getShoes()
                .get(0)
                .getColor(), BLACK);
        assertEquals(stockState.getShoes()
                .get(0)
                .getSize(), 40);
        assertEquals(stockState.getShoes()
                .get(0)
                .getQuantity(), 30);
    }

    @Test
    void updateStockForExistingShoeShouldThrowExceptionIfStockMaxCapacityIsExceeded() {
        // Given
        ShoeEntity shoeEntity = ShoeEntity.builder()
                .name("Kalenji")
                .color(Color.BLACK)
                .size(40)
                .quantity(30)
                .build();
        ShoeEntity existingShoeEntity = ShoeEntity.builder()
                .name("Kalenji")
                .color(Color.BLACK)
                .size(40)
                .quantity(10)
                .build();
        UpdatedStock updatedStock = UpdatedStock.builder()
                .name("Kalenji")
                .color(BLACK)
                .size(40)
                .quantity(30)
                .build();
        when(stockShoeRepository.getCurrentQuantityInStock()).thenReturn(Optional.of(30));
        when(stockShoeRepository.findByNameEqualsAndColorEqualsAndSizeEquals(any(), any(), any())).thenReturn(Optional.of(existingShoeEntity));
        when(stockMapper.updatedStockToShoeEntity(updatedStock)).thenReturn(shoeEntity);

        // When -> Then
        assertThrows(MaxStockCapacityExceededException.class, () -> stockCore.updateStock(updatedStock));
    }


    @Test
    void updateStockForNewShoeShouldThrowExceptionIfStockMaxCapacityIsExceeded() {
        // Given
        ShoeEntity shoeEntity = ShoeEntity.builder()
                .name("Kalenji")
                .color(Color.BLACK)
                .size(40)
                .quantity(30)
                .build();
        UpdatedStock updatedStock = UpdatedStock.builder()
                .name("Kalenji")
                .color(BLACK)
                .size(40)
                .quantity(30)
                .build();
        when(stockShoeRepository.getCurrentQuantityInStock()).thenReturn(Optional.of(30));
        when(stockShoeRepository.findByNameEqualsAndColorEqualsAndSizeEquals(any(), any(), any())).thenReturn(Optional.empty());
        when(stockMapper.updatedStockToShoeEntity(updatedStock)).thenReturn(shoeEntity);

        // When -> Then
        assertThrows(MaxStockCapacityExceededException.class, () -> stockCore.updateStock(updatedStock));
    }

    @Test
    void updateStockForExistingShoeShouldUpdateStock() {
        // Given
        ShoeEntity shoeEntity = ShoeEntity.builder()
                .name("Kalenji")
                .color(Color.BLACK)
                .size(40)
                .quantity(19)
                .build();
        ShoeEntity existingShoeEntity = ShoeEntity.builder()
                .name("Kalenji")
                .color(Color.BLACK)
                .size(40)
                .quantity(10)
                .build();
        UpdatedStock updatedStock = UpdatedStock.builder()
                .name("Kalenji")
                .color(BLACK)
                .size(40)
                .quantity(19)
                .build();
        StockShoe stockShoe = StockShoe.builder()
                .size(40)
                .color(BLACK)
                .quantity(19)
                .build();
        when(stockShoeRepository.getCurrentQuantityInStock()).thenReturn(Optional.of(10));
        when(stockShoeRepository.save(any())).thenReturn(shoeEntity);
        when(stockShoeRepository.findByNameEqualsAndColorEqualsAndSizeEquals(any(), any(), any())).thenReturn(Optional.of(existingShoeEntity));
        when(stockMapper.shoeToStockShoeDTO(any())).thenReturn(stockShoe);
        when(stockMapper.updatedStockToShoeEntity(updatedStock)).thenReturn(shoeEntity);

        // When
        StockShoe updatedShoeQuantity = stockCore.updateStock(updatedStock);

        // Then
        assertEquals(updatedShoeQuantity.getColor(), BLACK);
        assertEquals(updatedShoeQuantity.getSize(), 40);
        assertEquals(updatedShoeQuantity.getQuantity(), 19);
    }

    @Test
    void updateStockForNewShoeShouldUpdateStock() {
        // Given
        ShoeEntity shoeEntity = ShoeEntity.builder()
                .name("Kalenji")
                .color(Color.BLACK)
                .size(40)
                .quantity(19)
                .build();
        UpdatedStock updatedStock = UpdatedStock.builder()
                .name("Kalenji")
                .color(BLACK)
                .size(40)
                .quantity(19)
                .build();
        StockShoe stockShoe = StockShoe.builder()
                .size(40)
                .color(BLACK)
                .quantity(19)
                .build();
        when(stockShoeRepository.getCurrentQuantityInStock()).thenReturn(Optional.of(10));
        when(stockShoeRepository.save(any())).thenReturn(shoeEntity);
        when(stockShoeRepository.findByNameEqualsAndColorEqualsAndSizeEquals(any(), any(), any())).thenReturn(Optional.empty());
        when(stockMapper.shoeToStockShoeDTO(any())).thenReturn(stockShoe);
        when(stockMapper.updatedStockToShoeEntity(updatedStock)).thenReturn(shoeEntity);

        // When
        StockShoe updatedShoeQuantity = stockCore.updateStock(updatedStock);

        // Then
        assertEquals(updatedShoeQuantity.getColor(), BLACK);
        assertEquals(updatedShoeQuantity.getSize(), 40);
        assertEquals(updatedShoeQuantity.getQuantity(), 19);
    }
}
