package com.example.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.time.LocalDate;

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
		assertThat(stockRepository.count() == 3).isTrue();
	}

	@Test
	public void verifieLeNombreDeModelDeChaussureDoitRetournerLNombreTotaldesModelsTest() {
		assertThat(shoeRepository.count() == 15).isTrue();
	}

	@Test
	public void recupererLeStockLePlusRecent() {

		StockEntity stockRecent = stockRepository.getCurrentStock();

		assertThat(stockRecent).isNotNull();
		assertThat(stockRecent.getCreationDate().isEqual(LocalDate.parse("2021-05-01"))).isTrue();
	}

	@Test
	public void supprimerUnAncienStockEtVerifierQuIlResteUnSeulTest() {
		this.shoeRepository.deleteShoeWithIdStock(BigInteger.valueOf(1));
		this.stockRepository.deleteById(BigInteger.valueOf(1));
		assertThat(stockRepository.count() == 2).isTrue();

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
		assertThat(shoeRepository.count() == 14).isTrue();
		assertThat(this.stockRepository.count() == 3).isTrue();
	}

	@Test
	public void recupererLeStockAvecLesChaussures() {
		assertThat(stockRepository.count() == 3).isTrue();
		StockEntity stockEntity = this.stockRepository.getCurrentStockWithShoes();
		assertThat(stockEntity.getShoesEntity().isEmpty()).isFalse();

	}

}
