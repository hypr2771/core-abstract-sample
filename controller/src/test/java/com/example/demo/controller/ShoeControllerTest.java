package com.example.demo.controller;

import com.example.demo.core.ShoeCoreLegacy;
import com.example.demo.core.ShoeCoreNew;
import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Shoes;
import com.example.demo.entity.ShoeWithQuantity.Color;
import com.example.demo.facade.ShoeFacade;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShoeController.class)
class ShoeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ShoeFacade shoeFacade;

    @MockBean
    ShoeCoreLegacy shoeCoreLegacy;

    @MockBean
    ShoeCoreNew shoeCoreNew;

    @Test
    void getLegacyShoe() throws Exception {
        when(shoeFacade.get(1)).thenReturn(shoeCoreLegacy);
        when(shoeCoreLegacy.search(ArgumentMatchers.any(ShoeFilter.class))).thenReturn(Shoes.builder()
                .shoes(List.of(Shoe.builder()
                        .name("Legacy shoe")
                        .color(Color.BLUE)
                        .size(BigInteger.ONE)
                        .build()))
                .build());
        mockMvc.perform(get("/shoes/search")
                        .header("version", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shoes", Matchers.hasSize(1)));
    }

    @Test
    void getNewShoe() throws Exception {
        when(shoeFacade.get(2)).thenReturn(shoeCoreNew);
        when(shoeCoreNew.search(ArgumentMatchers.any(ShoeFilter.class))).thenReturn(Shoes.builder()
                .shoes(List.of(Shoe.builder()
                        .name("New shoe")
                        .color(Color.BLACK)
                        .size(BigInteger.TWO)
                        .build()))
                .build());
        mockMvc.perform(get("/shoes/search")
                        .header("version", 2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shoes", Matchers.hasSize(1)));
    }
}