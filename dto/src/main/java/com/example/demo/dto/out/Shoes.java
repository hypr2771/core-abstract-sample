package com.example.demo.dto.out;

import com.example.demo.dto.out.Shoes.ShoesBuilder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@Builder
@Getter
@Setter
@JsonDeserialize(builder = ShoesBuilder.class)
public class Shoes {

	List<Shoe> shoes;

	@JsonPOJOBuilder(withPrefix = "")
	public static class ShoesBuilder {

	}

}
