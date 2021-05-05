package com.example.demo.repositories;

import java.math.BigInteger;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.ShoeEntity;

public interface ShoeRepository extends JpaRepository<ShoeEntity, BigInteger> {

	@Transactional
	Long deleteByStockId(BigInteger idStock);
 
}
