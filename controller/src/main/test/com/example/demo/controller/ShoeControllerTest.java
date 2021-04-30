package com.example.demo.controller;

import com.example.demo.ShoeApiEndpoints;
import com.example.demo.dto.out.Shoes;
import com.example.demo.facade.ShoeFacade;
import com.example.demo.fixture.ShoesFixture;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ShoeController.class)
@Import(ShoeFacade.class)
public class ShoeControllerTest extends AbstractShoeTest {

    @Test
    public void shouldReturnOkStatusAndNotEmptyStockWhenVersionIsOneAndStockNotEmpty() throws Exception {
        Shoes shoesList = ShoesFixture.getShoesExisting();
        String shoesListAsJson = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(shoesList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ShoeApiEndpoints.SHOES_SEARCH)
                        .header(VERSION, 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(shoesListAsJson));
    }

    public void shouldReturnOkStatusAndEmptyStockWhenVersionIsOneAndStockIsEmpty() {
    }
}
