package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.core.StockCore;
import com.example.demo.dto.common.Color;
import com.example.demo.dto.common.State;
import com.example.demo.dto.out.StockShoe;
import com.example.demo.dto.out.StockState;
import com.example.demo.facade.Facade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class StockControllerTest {

    @InjectMocks
    private StockController stockController;

    @Mock
    private Facade<StockCore> stockFacade;

    @Mock
    private StockCore stockCore;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stockController)
                .build();
    }

    @Test
    void getStockStateShouldRespondSuccessfullyAndReturnAResponse() throws Exception {

        // Given
        List<StockShoe> stockShoes = Collections.singletonList(StockShoe.builder()
                .quantity(0)
                .color(Color.BLACK)
                .size(40)
                .build());
        StockState stockState = StockState.builder()
                .state(State.EMPTY)
                .shoes(stockShoes)
                .build();
        when(stockFacade.get(1)).thenReturn(stockCore);
        when(stockCore.getStockState()).thenReturn(stockState);


        // When
        ResultActions resultActions = mockMvc.perform(get("/stock").header("version", 1));


        // Then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.state").value("EMPTY"));
        resultActions.andExpect(jsonPath("$.shoes[0].size").value(40));
        resultActions.andExpect(jsonPath("$.shoes[0].color").value("BLACK"));
        resultActions.andExpect(jsonPath("$.shoes[0].quantity").value(0));

    }
}
