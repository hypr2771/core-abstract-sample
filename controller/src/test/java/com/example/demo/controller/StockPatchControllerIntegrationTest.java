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
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StockPatchControllerIntegrationTest {

    @Value("classpath:patch_requests_for_integration_tests/initializeShoes.json")
    Resource addShoesResource;

    @Value("classpath:patch_requests_for_integration_tests/addOneShoe.json")
    Resource addOneShoeResource;

    @Value("classpath:patch_requests_for_integration_tests/addAlreadyExistingShoe.json")
    Resource addAlreadyExistingShoeResource;

    @Value("classpath:patch_requests_for_integration_tests/updateOneShoeQuantity.json")
    Resource updateOneShoeQuantityResource;

    @Autowired
    StockController stockController;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void givenEmptyStock_WhenAddNewShoes_ShouldContainThoseShoes() throws Exception {
        Stock result = stockController.patch(2, JsonPatch.fromJson(objectMapper.readTree(addShoesResource.getFile())));
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getShoes().size()).isEqualTo(3);
        Assertions.assertThat(result.getState()).isEqualTo(com.example.demo.entity.Stock.State.SOME);
    }

    @Test
    void givenStockWithShoes_WhenAddNewShoe_ShouldHaveShoesWithTheNewAddedOne() throws Exception {
        // initialize stock
        stockController.patch(2, JsonPatch.fromJson(objectMapper.readTree(addShoesResource.getFile())));
        // add one new shoe
        Stock result = stockController.patch(2, JsonPatch.fromJson(objectMapper.readTree(addOneShoeResource.getFile())));
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getShoes().size()).isEqualTo(4);
        Assertions.assertThat(result.getState()).isEqualTo(com.example.demo.entity.Stock.State.SOME);
    }

    @Test
    void givenStockWithShoes_WhenAddSameShoe_ShouldThrowError() throws Exception {
        // initialize stock
        stockController.patch(2, JsonPatch.fromJson(objectMapper.readTree(addShoesResource.getFile())));
        // add already existing shoe
        JsonPatch addAlreadyExistingShoePatch = JsonPatch.fromJson(objectMapper.readTree(addAlreadyExistingShoeResource.getFile()));
        org.junit.jupiter.api.Assertions.assertThrows(InvalidDataAccessApiUsageException.class,
                () -> stockController.patch(2, addAlreadyExistingShoePatch));

    }

    @Test
    void givenStockWithShoes_WhenUpdateQuantityOneShoe_ShouldBeOk() throws Exception {
        // initialize stock
        stockController.patch(2, JsonPatch.fromJson(objectMapper.readTree(addShoesResource.getFile())));
        Stock result = stockController.patch(2, JsonPatch.fromJson(objectMapper.readTree(updateOneShoeQuantityResource.getFile())));
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getShoes().size()).isEqualTo(3);
        Assertions.assertThat(result.getShoes().get(2).getQuantity()).isEqualTo(30);
    }
}