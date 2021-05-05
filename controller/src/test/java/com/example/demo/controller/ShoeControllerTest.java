package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
public class ShoeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	//@Test TODO
	public void getStockTest() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/shoes/stock")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();

		String resultDOW = result.getResponse().getContentAsString();
		assertThat(resultDOW).isNotNull();

	}
}
