package com.example.demo.repository;

import com.example.demo.entity.ShoeWithQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface ShoeWithQuantityRepository extends JpaRepository<ShoeWithQuantity, Long> {

    Optional<ShoeWithQuantity> findBySizeAndColor(BigInteger size, ShoeWithQuantity.Color color);
}
