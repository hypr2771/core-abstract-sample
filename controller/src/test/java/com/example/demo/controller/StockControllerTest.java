package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.dto.out.Shoes;
import com.example.demo.dto.out.Stock;
import com.example.demo.dto.out.Stock.State;
import com.example.demo.services.IStockService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest
public class StockControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private StockController stockController;

	@MockBean
	private IStockService stockService;

	private Stock stock;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();

		stock = Stock.builder().shoes(Shoes.builder().shoes(Collections.emptyList()).build()).state(State.EMPTY)
				.totalQuantity(BigInteger.ZERO).build();
	}

	@Test
	public void getStockTest() throws Exception {

		Mockito.when(stockService.getStock()).thenReturn(stock);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/shoes/stock").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.state", is(this.stock.getState().toString())))
				.andExpect(jsonPath("$.shoes.shoes", is(this.stock.getShoes().getShoes())))
				.andExpect(jsonPath("$.totalQuantity", is(this.stock.getTotalQuantity().intValue())))
				.andExpect(jsonPath("$.creationDate", is(this.stock.getCreationDate())));

	}

	@Test
	public void updateStockTest() throws Exception {

		Mockito.when(this.stockService.updateStock(this.stock)).thenReturn(stock);
		this.mockMvc
				.perform(MockMvcRequestBuilders.patch("/shoes/stock").contentType(MediaType.APPLICATION_JSON)
						.content(convertObjectToJson(this.stock)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.state", is(this.stock.getState().toString())))
				.andExpect(jsonPath("$.shoes.shoes", is(this.stock.getShoes().getShoes())))
				.andExpect(jsonPath("$.totalQuantity", is(this.stock.getTotalQuantity().intValue())))
				.andExpect(jsonPath("$.creationDate", is(this.stock.getCreationDate())));

	}

	/**
	 * Méthode pour convertir le flux JSON en Bytes
	 * 
	 * @param object
	 * @return byte[]
	 * @throws IOException
	 */
	private byte[] convertObjectToJson(Object object) throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(Include.NON_NULL);

		return mapper.writeValueAsBytes(object);
	}
}
