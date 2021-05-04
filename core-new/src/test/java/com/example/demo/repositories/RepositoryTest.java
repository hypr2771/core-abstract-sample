package com.example.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.example.demo.entities.ShoeEntity;
import com.example.demo.entities.StockEntity;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class RepositoryTest {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ShoeRepository shoeRepository;
	
	@BeforeEach
	public void initDB() {
		
	}

	@Test
	public void verifieLeNombreDhistoriqueDeStockDoitRetourner3Test() {
		List<StockEntity> stocks =   this.stockRepository.findAll();
		assertThat(stocks).isNotEmpty();
		assertThat(stocks.size()).isEqualTo(3);	}

	@Test
	public void verifieLeNombreDeModelDeChaussureDoitRetournerLNombreTotaldesModelsTest() {
		List<ShoeEntity> shoes =   this.shoeRepository.findAll();
		assertThat(shoes).isNotEmpty();
		assertThat(shoes.size()).isEqualTo(15);
	}

	@Test
	public void recupererLeStockLePlusRecent() {

		StockEntity stockRecent = stockRepository.getCurrentStock();

		assertThat(stockRecent).isNotNull();
		assertThat(stockRecent.getCreationDate()).isEqualTo(LocalDate.parse("2021-05-01"));
	}

	@Test
	public void supprimerUnAncienStockEtVerifierQuIlResteUnSeulTest() {
		this.shoeRepository.deleteByStockId(BigInteger.valueOf(1));
		this.stockRepository.deleteById(BigInteger.valueOf(1));
		assertThat(stockRepository.count()).isEqualTo(2);

	}

	@Test
	@Transactional
	public void supprimerUnModelDeChaussureEtMettreAJourLeStock() {
		ShoeEntity shoe = this.shoeRepository.getOne(BigInteger.valueOf(3));

		StockEntity stockEntity = this.stockRepository.getCurrentStock();
		stockEntity.setTotalQuantity(stockEntity.getTotalQuantity().intValue() - shoe.getQuantity().intValue());
		stockEntity.setCreationDate(LocalDate.now());

		this.shoeRepository.deleteById(BigInteger.valueOf(3));
		this.stockRepository.save(stockEntity);
		assertThat(shoeRepository.count()).isEqualTo(14);
		assertThat(this.stockRepository.count()).isEqualTo(3);
	}

	@Test
	public void recupererLeStockAvecLesChaussures() {
		assertThat(stockRepository.count()).isEqualTo(3);
		StockEntity stockEntity = this.stockRepository.getCurrentStockWithShoes();
		assertThat(stockEntity.getShoesEntity()).isNotEmpty();

	}

}
