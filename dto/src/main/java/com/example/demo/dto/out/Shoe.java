package com.example.demo.dto.out;

import java.math.BigInteger;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.demo.dto.in.ShoeFilter.Color;
import com.example.demo.dto.out.Shoe.ShoeBuilder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = ShoeBuilder.class)
public class Shoe {

	private String name;
	
	@NotNull
	@NotEmpty
	@Min(13)
	@Max(60)
	private BigInteger size;
	
	@NotNull
	@NotEmpty
	private Color color;
	
	@NotNull
	@NotEmpty
	@Min(0)
	@Max(30)
	private BigInteger quantity;

	@JsonPOJOBuilder(withPrefix = "")
	public static class ShoeBuilder {

	}

}
