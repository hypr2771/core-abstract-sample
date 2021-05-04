package com.example.demo.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dto.in.ShoeFilter.Color;
import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Shoe.ShoeBuilder;
import com.example.demo.dto.out.Shoes;
import com.example.demo.dto.out.Shoes.ShoesBuilder;
import com.example.demo.dto.out.Stock;
import com.example.demo.dto.out.Stock.State;
import com.example.demo.dto.out.Stock.StockBuilder;
import com.example.demo.entities.StockEntity;
import com.example.demo.mapper.StockMapper;
import com.example.demo.repositories.StockRepository;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class StockServiceImplTest {
	
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
		stockEntity.setId(BigInteger.valueOf(1));

		StockBuilder stockBuilder = Stock.builder();
		ShoesBuilder shoesBuilder = Shoes.builder();
		
		List<Shoe> listShoes = new ArrayList<Shoe>();
		ShoeBuilder shoeBuilder = Shoe.builder();
		shoeBuilder.color(Color.BLACK);
		shoeBuilder.name("model1");
		shoeBuilder.quantity(BigInteger.valueOf(10));
		shoeBuilder.size(BigInteger.valueOf(42));
		Shoe shoe = shoeBuilder.build();
		listShoes.add(shoe );
		shoesBuilder.shoes(listShoes );
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
	void updateStockTestWithSOMEState() {
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

}
