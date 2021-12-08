package com.example.demo.controller;

import com.example.demo.core.StockCoreLegacy;
import com.example.demo.core.StockCoreNew;
import com.example.demo.dto.out.ShoeWithQuantity;
import com.example.demo.dto.out.Stock;
import com.example.demo.facade.StockFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigInteger;
import java.util.List;

import static com.example.demo.entity.ShoeWithQuantity.Color;
import static com.example.demo.entity.Stock.State;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StockController.class)
class StockGetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StockFacade stockFacade;

    @MockBean
    StockCoreLegacy stockCoreLegacy;

    @MockBean
    StockCoreNew stockCoreNew;

    @Test
    void getLegacyStock() throws Exception {
        when(stockFacade.get(1)).thenReturn(stockCoreLegacy);
        when(stockCoreLegacy.get()).thenReturn(Stock.builder()
                .state(State.SOME)
                .shoes(List.of(ShoeWithQuantity.builder()
                        .color(Color.BLACK)
                        .size(BigInteger.valueOf(40))
                        .quantity(BigInteger.valueOf(10))
                        .build()))
                .build());
        mockMvc.perform(get("/stock")
                        .header("version", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value("SOME"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shoes", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shoes[0].color").value("BLACK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shoes[0].size").value("40"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shoes[0].quantity").value("10"));
    }

    @Test
    void getNewStock() throws Exception {
        when(stockFacade.get(2)).thenReturn(stockCoreNew);
        when(stockCoreNew.get()).thenReturn(Stock.builder()
                .state(State.SOME)
                .shoes(List.of(ShoeWithQuantity.builder()
                        .color(Color.BLUE)
                        .size(BigInteger.valueOf(39))
                        .quantity(BigInteger.valueOf(10))
                        .build()))
                .build());
        mockMvc.perform(get("/stock")
                        .header("version", 2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value("SOME"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shoes", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shoes[0].color").value("BLUE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shoes[0].size").value("39"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shoes[0].quantity").value("10"));
    }
}