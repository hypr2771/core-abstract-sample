package com.example.demo.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.demo.dto.in.ShoeFilter.Color;
import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Shoes;
import com.example.demo.dto.out.Stock;
import com.example.demo.dto.out.Stock.State;
import com.example.demo.entities.ShoeEntity;
import com.example.demo.entities.StockEntity;
import com.example.demo.mapper.StockMapper;
import com.example.demo.repositories.StockRepository;
import com.example.demo.services.exception.QuantityException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@PropertySource("classpath:messages.properties")
class StockServiceImplTest {

	private static final String MESSAGE_ERROR_CAPACITY = "Verify the total quantity or the the total sum of the shoes, can't be more than 30 or it's equal to: ";

	@InjectMocks
	private StockServiceImpl stockServiceImpl;

	@MockBean
	private StockRepository stockRepository;

	@MockBean
	private StockMapper stockMapper;

	private StockEntity stockEntity;

	private Stock stock;

	@BeforeEach
	public void setUp() {
		stockEntity = new StockEntity();
		stockEntity.setId(BigInteger.ONE);

		Shoe shoe = Shoe.builder().size(BigInteger.valueOf(42)).color(Color.BLACK).name("model1")
				.quantity(BigInteger.TEN).build();
		Shoes shoes = Shoes.builder().shoes(List.of(shoe)).build();

		this.stock = Stock.builder().creationDate(LocalDate.now()).state(State.SOME).shoes(shoes).build();

	}

	@Test
	void getStockTestWithStateSOME() {

		when(this.stockMapper.stockEntityToStock(this.stockEntity)).thenReturn(this.stock);
		when(this.stockRepository.getCurrentStockWithShoes()).thenReturn(this.stockEntity);
		Stock stockResult = stockServiceImpl.getStock();

		assertThat(stockResult).isNotNull();
		assertThat(stockResult.getState()).isEqualTo(State.SOME);
		assertThat(stockResult.getShoes().getShoes()).isNotEmpty();
	}

	@Test
	void updateStockTestWithStateEMPTY() throws QuantityException {
		this.stockEntity.setCreationDate(LocalDate.now());
		this.stockEntity.setTotalQuantity(0);
		this.stockEntity.setShoesEntity(new HashSet<ShoeEntity>());

		Shoe shoe = Shoe.builder().size(BigInteger.valueOf(42)).color(Color.BLACK).name("model1")
				.quantity(BigInteger.ZERO).build();
		Shoes shoes = Shoes.builder().shoes(List.of(shoe)).build();

		this.stock = Stock.builder().totalQuantity(BigInteger.ZERO).creationDate(LocalDate.now()).state(State.EMPTY).shoes(shoes).build();

		when(this.stockMapper.stockToStockEntity(this.stock)).thenReturn(this.stockEntity);
		when(this.stockRepository.save(this.stockEntity)).thenReturn(this.stockEntity);
		when(this.stockMapper.stockEntityToStock(this.stockEntity)).thenReturn(this.stock);

		Stock stockResult = stockServiceImpl.updateStock(this.stock);

		assertThat(stockResult).isNotNull();
		assertThat(stockResult.getState()).isEqualTo(State.EMPTY);
		assertThat(stockResult.getTotalQuantity()).isZero();
		assertThat(LocalDate.now()).isEqualTo(stockResult.getCreationDate());
	}

	@Test
	void updateStockTestWithSOMEState() throws QuantityException {
		this.stockEntity.setCreationDate(LocalDate.now());
		when(this.stockMapper.stockToStockEntity(this.stock)).thenReturn(this.stockEntity);
		when(this.stockRepository.save(this.stockEntity)).thenReturn(this.stockEntity);
		when(this.stockMapper.stockEntityToStock(this.stockEntity)).thenReturn(this.stock);

		Stock stockResult = stockServiceImpl.updateStock(this.stock);

		assertThat(stockResult).isNotNull();
		assertThat(stockResult.getState()).isEqualTo(State.SOME);
		assertThat(stockResult.getShoes().getShoes()).isNotEmpty();
		assertThat(LocalDate.now()).isEqualTo(stockResult.getCreationDate());
	}

	@Test
	void updateStockTestQuantityExceptionMoreThan30() {
		this.stockEntity.setCreationDate(LocalDate.now());
		this.stockEntity.setTotalQuantity(35);
		this.stockEntity.setShoesEntity(new HashSet<ShoeEntity>());
		;

		Shoe shoe = Shoe.builder().size(BigInteger.valueOf(42)).color(Color.BLACK).name("model1")
				.quantity(BigInteger.valueOf(35)).build();
		Shoes shoes = Shoes.builder().shoes(List.of(shoe)).build();

		this.stock = Stock.builder().creationDate(LocalDate.now()).totalQuantity(BigInteger.valueOf(35))
				.state(State.FULL).shoes(shoes).build();

		when(this.stockMapper.stockToStockEntity(this.stock)).thenReturn(this.stockEntity);
		when(this.stockRepository.save(this.stockEntity)).thenReturn(this.stockEntity);
		when(this.stockMapper.stockEntityToStock(this.stockEntity)).thenReturn(this.stock);

		Stock stockResult = null;
		boolean exceptionGenere = false;

		ReflectionTestUtils.setField(stockServiceImpl, "messageCapacity", MESSAGE_ERROR_CAPACITY);

		try {
			stockResult = stockServiceImpl.updateStock(this.stock);
		} catch (QuantityException e) {

			exceptionGenere = true;
			assertThat(MESSAGE_ERROR_CAPACITY + this.stock.getTotalQuantity().intValue()).isEqualTo(e.getMessage());
		}

		assertThat(exceptionGenere).isTrue();
		assertThat(stockResult).isNull();

	}

}
