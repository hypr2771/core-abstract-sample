package com.example.demo.repository;

import com.example.demo.entity.ShoeWithQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeWithQuantityRepository extends JpaRepository<ShoeWithQuantity, Long> {
}
