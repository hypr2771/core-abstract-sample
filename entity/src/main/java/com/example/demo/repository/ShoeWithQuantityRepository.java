package com.example.demo.repository;

import com.example.demo.entity.ShoeWithQuantity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeWithQuantityRepository extends CrudRepository<ShoeWithQuantity, Long> {
}
