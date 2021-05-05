package com.example.demo.services.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuantityException extends Exception {

	private static final long serialVersionUID = -772506312315983110L;

	private String message;
	
	private Integer code;
	
	
	
	
}
