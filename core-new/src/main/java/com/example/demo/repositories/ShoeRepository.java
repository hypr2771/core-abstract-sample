package com.example.demo.repositories;

import java.math.BigInteger;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.ShoeEntity;

public interface ShoeRepository extends JpaRepository<ShoeEntity, BigInteger> {

	@Query("delete from ShoeEntity s where s.stock.id =:idStock")
	@Transactional
	@Modifying
	void deleteShoeWithIdStock(@Param("idStock") BigInteger idStock);
 
}
