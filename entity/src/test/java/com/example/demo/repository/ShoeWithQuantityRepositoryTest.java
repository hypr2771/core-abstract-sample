package com.example.demo.repository;

import com.example.demo.entity.ShoeWithQuantity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ShoeWithQuantityRepositoryTest {
    @Autowired
    private ShoeWithQuantityRepository shoeWithQuantityRepository;

    @Test
    void whenFindingCustomerById_thenCorrect() {
        shoeWithQuantityRepository.save(new ShoeWithQuantity(BigInteger.ONE, ShoeWithQuantity.Color.BLACK, BigInteger.ONE));
        assertThat(shoeWithQuantityRepository.findById(1L)).isInstanceOf(Optional.class);
    }

}