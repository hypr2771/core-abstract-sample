package com.example.demo.dto.out;

import java.util.List;

import com.example.demo.dto.out.Shoes.ShoesBuilder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = ShoesBuilder.class)
public class Shoes {

	List<Shoe> shoes;

	@JsonPOJOBuilder(withPrefix = "")
	public static class ShoesBuilder {

	}

}
