package com.example.demo.controller;

import com.example.demo.core.StockCoreNew;
import com.example.demo.dto.out.Stock;
import com.example.demo.facade.StockFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.util.List;

import static com.example.demo.entity.Stock.State;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StockController.class)
class StockPatchControllerTest {

    @Value("classpath:patch_requests/initializeShoes.json")
    Resource addShoesResource;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StockFacade stockFacade;

    @MockBean
    StockCoreNew stockCoreNew;


    @Test
    void addOneShoeToStock() throws Exception {
        when(stockFacade.get(2)).thenReturn(stockCoreNew);
        when(stockCoreNew.get()).thenReturn(Stock.builder()
                .state(State.EMPTY)
                .shoes(List.of())
                .build());
        mockMvc.perform(patch("/stock")
                        .header("version", 2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Files.readString(addShoesResource.getFile().toPath())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}