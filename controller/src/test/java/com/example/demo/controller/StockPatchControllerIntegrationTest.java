package com.example.demo.controller;

import com.example.demo.dto.out.Stock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StockPatchControllerIntegrationTest {

    @Value("classpath:patch_requests/initializeShoes.json")
    Resource addShoesResource;

    @Value("classpath:patch_requests/addOneShoe.json")
    Resource addOneShoeResource;

    @Value("classpath:patch_requests/addAlreadyExistingShoe.json")
    Resource addAlreadyExistingShoeResource;

    @Autowired
    StockController stockController;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void givenEmptyStock_WhenAddNewShoes_ShouldContainThoseShoes() throws Exception {
        ResponseEntity<Stock> result = stockController.patch(2, JsonPatch.fromJson(objectMapper.readTree(addShoesResource.getFile())));
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getBody().getShoes().size()).isEqualTo(3);
        Assertions.assertThat(result.getBody().getState()).isEqualTo(com.example.demo.entity.Stock.State.SOME);
    }

    @Test
    void givenStockWithShoes_WhenAddNewShoe_ShouldHaveShoesWithTheNewAddedOne() throws Exception {
        // initialize stock
        stockController.patch(2, JsonPatch.fromJson(objectMapper.readTree(addShoesResource.getFile())));
        // add one new shoe
        ResponseEntity<Stock> result = stockController.patch(2, JsonPatch.fromJson(objectMapper.readTree(addOneShoeResource.getFile())));
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getBody().getShoes().size()).isEqualTo(4);
        Assertions.assertThat(result.getBody().getState()).isEqualTo(com.example.demo.entity.Stock.State.SOME);
    }

    @Test
    void givenStockWithShoes_WhenAddSameShoe_ShouldThrowError() throws Exception {
        // initialize stock
        stockController.patch(2, JsonPatch.fromJson(objectMapper.readTree(addShoesResource.getFile())));
        // add already existing shoe
        ResponseEntity<Stock> result = stockController.patch(2, JsonPatch.fromJson(objectMapper.readTree(addAlreadyExistingShoeResource.getFile())));
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);

    }
}