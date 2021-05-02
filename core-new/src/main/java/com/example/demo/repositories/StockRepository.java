package com.example.demo.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.StockEntity;


public interface StockRepository extends JpaRepository<StockEntity, BigInteger> {

	@Query("select s from StockEntity s where s.creationDate = (select Max(creationDate) from StockEntity)")
	StockEntity getCurrentStock();

}
