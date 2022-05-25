package com.example.demo.repository;

import com.example.demo.entity.Color;
import com.example.demo.entity.ShoeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockShoeRepository extends JpaRepository<ShoeEntity, Long> {

    @Query("select sum(shoe.quantity) from ShoeEntity shoe")
    Optional<Integer> getCurrentQuantityInStock();

    Optional<ShoeEntity> findByNameEqualsAndColorEqualsAndSizeEquals(String name, Color color, Integer size);

}
