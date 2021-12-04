package com.example.demo.repository;

import com.example.demo.entity.ShoeWithQuantity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ShoeWithQuantityRepositoryTest {
    @Autowired
    private ShoeWithQuantityRepository shoeWithQuantityRepository;

    @Test
    void whenSaveAShoeThenCouldRetrieveIt() {
        shoeWithQuantityRepository.save(ShoeWithQuantity.builder()
                .color(ShoeWithQuantity.Color.BLACK)
                .quantity(BigInteger.TEN)
                .size(BigInteger.TWO)
                .build());
        assertThat(shoeWithQuantityRepository.findById(1L)).isPresent();
    }

}