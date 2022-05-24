package com.example.demo.controller;

import com.example.demo.core.StockCore;
import com.example.demo.dto.out.StockState;
import com.example.demo.facade.Facade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/stock")
@RequiredArgsConstructor
public class StockController {

    private final Facade<StockCore> stockFacade;

    @GetMapping
    public ResponseEntity<StockState> getStockState(@RequestHeader Integer version) {
        return ResponseEntity.ok(stockFacade.get(version).getStockState());
    }
}
