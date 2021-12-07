package com.example.demo.repository;

import com.example.demo.entity.ShoeWithQuantity;
import com.example.demo.entity.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StockRepositoryTest {
    @Autowired
    StockRepository stockRepository;

    @Test
    void whenSaveAStockThenCouldRetrieveIt() {
        Stock aStock = Stock.builder()
                .build();
        stockRepository.saveAndFlush(aStock);
        Optional<Stock> byId = stockRepository.findById(aStock.getId());
        assertThat(byId).isPresent();
        assertThat(byId.get().getState()).isEqualTo(Stock.State.EMPTY);
    }

    @Test
    void whenSaveAStockWithLessThan30ShoesThenShouldWork() {
        ShoeWithQuantity aShoe = ShoeWithQuantity.builder()
                .size(BigInteger.ONE)
                .quantity(BigInteger.TEN)
                .color(ShoeWithQuantity.Color.BLUE).build();
        ShoeWithQuantity anotherShoe = ShoeWithQuantity.builder()
                .size(BigInteger.TWO)
                .quantity(BigInteger.TWO)
                .color(ShoeWithQuantity.Color.BLACK).build();
        Stock aStock = Stock.builder()
                .shoes(List.of(aShoe, anotherShoe))
                .build();
        stockRepository.saveAndFlush(aStock);
        Optional<Stock> byId = stockRepository.findById(aStock.getId());
        assertThat(byId).isPresent();
        assertThat(byId.get().getState()).isEqualTo(Stock.State.SOME);
    }

    @Test
    void whenSaveAStockWithMoreThan30ShoesThenShouldThrowError() {
        List<ShoeWithQuantity> listOfShoes = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            listOfShoes.add(ShoeWithQuantity.builder()
                    .size(BigInteger.valueOf(i))
                    .quantity(BigInteger.TEN)
                    .color(ShoeWithQuantity.Color.BLUE)
                    .build());
        }
        Stock aStock = Stock.builder()
                .shoes(listOfShoes)
                .build();
        Assertions.assertThrows(ConstraintViolationException.class, () -> stockRepository.saveAndFlush(aStock));
    }

}