package com.example.demo.dto.out;

import java.math.BigInteger;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.demo.dto.in.ShoeFilter.Color;
import com.example.demo.dto.out.Shoe.ShoeBuilder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Value
@Builder
@JsonDeserialize(builder = ShoeBuilder.class)
public class Shoe {

	private String name;
	@NotNull
	@NotEmpty
	private BigInteger size;
	
	@NotNull
	@NotEmpty
	private Color color;
	
	@NotNull
	@NotEmpty
	private BigInteger quantity;

	@JsonPOJOBuilder(withPrefix = "")
	public static class ShoeBuilder {

	}

}
