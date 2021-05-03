package com.example.demo.dto.out;

import java.math.BigInteger;
import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.demo.dto.out.Stock.StockBuilder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@Builder
@Getter
@Setter
@JsonDeserialize(builder = StockBuilder.class)
public class Stock {

	private State state;
	
	private Shoes shoes;

	@NotEmpty
	@NotNull
	@Max(value = 30, message = "La capacité maximale autorisée est de 30")
	private BigInteger totalQuantity;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate creationDate;

	public enum State {

		EMPTY, FULL, SOME;

	}
	
	@JsonPOJOBuilder(withPrefix = "")
	public static class StockBuilder {

	}

}
