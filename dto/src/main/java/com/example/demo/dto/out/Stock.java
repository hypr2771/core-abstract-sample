package com.example.demo.dto.out;

import com.example.demo.dto.out.Shoes.ShoesBuilder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = ShoesBuilder.class)
public class Stock {

	private State state;
	
	private Shoes shoes;

	public enum State {

		EMPTY, FULL, SOME;

	}

}
