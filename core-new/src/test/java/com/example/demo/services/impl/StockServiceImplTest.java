package com.example.demo.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
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
import com.example.demo.dto.out.Shoe.ShoeBuilder;
import com.example.demo.dto.out.Shoes;
import com.example.demo.dto.out.Shoes.ShoesBuilder;
import com.example.demo.dto.out.Stock;
import com.example.demo.dto.out.Stock.State;
import com.example.demo.dto.out.Stock.StockBuilder;
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

	private StockBuilder stockBuilder;

	private ShoesBuilder shoesBuilder;

	private ShoeBuilder shoeBuilder;

	@BeforeEach
	public void setUp() {
		stockEntity = new StockEntity();
		stockEntity.setId(BigInteger.valueOf(1));

		stockBuilder = Stock.builder();
		shoesBuilder = Shoes.builder();

		List<Shoe> listShoes = new ArrayList<Shoe>();
		shoeBuilder = Shoe.builder();
		shoeBuilder.color(Color.BLACK);
		shoeBuilder.name("model1");
		shoeBuilder.quantity(BigInteger.valueOf(10));
		shoeBuilder.size(BigInteger.valueOf(42));
		Shoe shoe = shoeBuilder.build();
		listShoes.add(shoe);
		shoesBuilder.shoes(listShoes);
		Shoes shoes = shoesBuilder.build();
		stockBuilder.shoes(shoes);
		stockBuilder.state(State.SOME);
		stockBuilder.creationDate(LocalDate.now());
		this.stock = stockBuilder.build();

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

		this.stockBuilder.totalQuantity(BigInteger.valueOf(0));
		this.stockBuilder.state(State.EMPTY);
		this.shoesBuilder.shoes(new ArrayList<Shoe>());
		Shoes shoes = this.shoesBuilder.build();
		this.stockBuilder.shoes(shoes);
		this.stock = this.stockBuilder.build();
		when(this.stockMapper.stockToStockEntity(this.stock)).thenReturn(this.stockEntity);
		when(this.stockRepository.save(this.stockEntity)).thenReturn(this.stockEntity);
		when(this.stockMapper.stockEntityToStock(this.stockEntity)).thenReturn(this.stock);

		Stock stockResult = stockServiceImpl.updateStock(this.stock);

		assertThat(stockResult).isNotNull();
		assertThat(stockResult.getState()).isEqualTo(State.EMPTY);
		assertThat(stockResult.getShoes().getShoes()).isEmpty();
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

		this.stockBuilder.totalQuantity(BigInteger.valueOf(35));
		this.stockBuilder.state(State.FULL);
		this.shoeBuilder.quantity(BigInteger.valueOf(35));
		Shoe shoe = this.shoeBuilder.build();
		List<Shoe> sohesList = new ArrayList<Shoe>();
		sohesList.add(shoe);
		this.shoesBuilder.shoes(sohesList);
		Shoes shoes = this.shoesBuilder.build();
		this.stockBuilder.shoes(shoes);
		this.stock = this.stockBuilder.build();
		when(this.stockMapper.stockToStockEntity(this.stock)).thenReturn(this.stockEntity);
		when(this.stockRepository.save(this.stockEntity)).thenReturn(this.stockEntity);
		when(this.stockMapper.stockEntityToStock(this.stockEntity)).thenReturn(this.stock);

		Stock stockResult = null;
		boolean exceptionGenere = false;
		
		ReflectionTestUtils.setField(stockServiceImpl, "messageCapacity",
				MESSAGE_ERROR_CAPACITY);
		
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
