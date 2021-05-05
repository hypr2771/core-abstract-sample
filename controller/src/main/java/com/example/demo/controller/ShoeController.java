package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.out.Shoes;
import com.example.demo.dto.out.Stock;
import com.example.demo.facade.ShoeFacade;
import com.example.demo.services.IStockService;
import com.example.demo.services.exception.QuantityException;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(path = "/shoes")
@RequiredArgsConstructor
public class ShoeController {

	private final ShoeFacade shoeFacade;

	@Autowired
	private IStockService stockService;

	@GetMapping(path = "/search")
	@ApiOperation(value = "Return all shoes models specify")
	public ResponseEntity<Shoes> all(ShoeFilter filter, @RequestHeader Integer version) {

		return ResponseEntity.ok(shoeFacade.get(version).search(filter));
	}

	@GetMapping(path = "/stock")
	@ApiOperation(value = "Return all stock shoes and status")
	public ResponseEntity<Stock> getStock() {

		return ResponseEntity.ok(stockService.getStock());

	}

	@PatchMapping(path = "/stock")
	@ApiOperation(value = "Update the stock (Two color accepted: BLACK and BLUE) and max capacity is 30")
	public ResponseEntity<?> updateStock(@RequestBody Stock stock) throws QuantityException {

			return ResponseEntity.ok(stockService.updateStock(stock));

	}

}
