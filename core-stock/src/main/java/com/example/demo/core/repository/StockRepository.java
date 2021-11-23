package com.example.demo.core.repository;

import com.example.demo.core.repository.model.StockModel;
import com.example.demo.core.repository.model.StockId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends MongoRepository<StockModel, StockId> {

}
