package com.example.demo.core.repository;

import com.example.demo.core.repository.entity.StockEntity;
import com.example.demo.core.repository.entity.StockId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends MongoRepository<StockEntity, StockId> {

}
