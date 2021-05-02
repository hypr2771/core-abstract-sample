package com.example.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entities.ShoeEntity;
import com.example.demo.entities.StockEntity;

@SpringBootTest
public class RepositoryTest {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ShoeRepository shoeRepository;


	@Test
	public void verifieLeNombreDhistoriqueDeStockDoitRetourner2Test() {
		assertThat(stockRepository.count() == 2);
	}

	@Test
	public void verifieLeNombreDeModelDeChaussureDoitRetourner14Test() {
		assertThat(shoeRepository.count() == 14);
	}

	@Test
	public void recupererLeStockLePlusRecent() {

		StockEntity stockRecent = stockRepository.getCurrentStock();

		assertThat(stockRecent).isNotNull();
		assertThat(stockRecent.getCreationDate().isEqual(LocalDate.parse("2021-05-01")));
	}

	@Test
	public void supprimerUnAncienStockEtVerifierQuIlResteUnSeulTest() {
		this.shoeRepository.deleteShoeWithIdStock(BigInteger.valueOf(1));
		this.stockRepository.deleteById(BigInteger.valueOf(1));
		assertThat(stockRepository.count() == 1);

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
		assertThat(shoeRepository.count() == 13);
		assertThat(this.stockRepository.count() == 3);
	}

}
