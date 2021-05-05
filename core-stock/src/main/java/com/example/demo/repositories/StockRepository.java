package com.example.demo.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.StockEntity;

public interface StockRepository extends JpaRepository<StockEntity, BigInteger> {

	
	@Query("SELECT s FROM StockEntity s "
			+ "WHERE s.creationDate = (select MAX(creationDate) FROM StockEntity) "
			)
	StockEntity getCurrentStock();
	@Query("SELECT s FROM StockEntity s "
			+ "INNER JOIN s.shoesEntity shoes "
			+ "WHERE s.creationDate = (select MAX(creationDate) FROM StockEntity) order by shoes.id"
			)
	StockEntity getCurrentStockWithShoes();

}
