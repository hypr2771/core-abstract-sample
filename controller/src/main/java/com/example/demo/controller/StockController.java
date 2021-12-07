package com.example.demo.controller;

import com.example.demo.dto.out.Stock;
import com.example.demo.facade.StockFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping(path = "/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockFacade stockFacade;

    @GetMapping
    public ResponseEntity<Stock> get(@RequestHeader Integer version) {
        return ResponseEntity.ok(stockFacade.get(version).get());
    }

    @PatchMapping
    public ResponseEntity<Stock> patch(@RequestHeader Integer version, @RequestBody JsonPatch patch) {
        try {
            return ResponseEntity.ok(stockFacade.get(version).patch(patch));
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


}
