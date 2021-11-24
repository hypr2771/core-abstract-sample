package com.example.demo.core.service;

import com.example.demo.core.mapper.StockServiceMapperImpl;
import com.example.demo.core.repository.StockRepository;
import com.example.demo.core.repository.entity.StockEntity;
import com.example.demo.core.repository.entity.StockId;
import com.example.demo.dto.common.StockShoe;
import com.example.demo.dto.out.Stock;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.Arrays;

import static com.example.demo.dto.out.Stock.State.FULL;
import static com.example.demo.dto.out.Stock.State.SOME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = StockServiceImpl.class)
public class StockServiceImplTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;

    @Spy
    private StockServiceMapperImpl stockMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSuccessGetStock(){
        val stockId = StockId.builder()
                .name("NikeSB")
                .size(BigInteger.valueOf(40l))
                .color("BLUE").build();
        val stock = StockEntity.builder()
                .stockId(stockId)
                .quantity(9).build();

        val stockList = Arrays.asList(stock);

        when(stockRepository.findAll()).thenReturn(stockList);

        val stockDTO = stockService.getStock();

        assertNotNull(stockDTO);
        assertEquals(stock.getStockId().getName(), stockDTO.getShoes().stream().findFirst().get().getName());
        assertEquals(stock.getStockId().getColor(), stockDTO.getShoes().stream().findFirst().get().getColor());
        assertEquals(stock.getStockId().getSize(), stockDTO.getShoes().stream().findFirst().get().getSize());
        assertEquals(stock.getQuantity(), stockDTO.getShoes().stream().findFirst().get().getQuantity());
        assertEquals(SOME, stockDTO.getState());
    }

    @Test
    public void shouldSuccessUpdateStock(){
        val stockShoe1 = StockShoe.builder()
                .name("Nike SB")
                .size(BigInteger.valueOf(40l))
                .color("BLUE")
                .quantity(10).build();

        val stockShoe2 = StockShoe.builder()
                .name("Adidas")
                .size(BigInteger.valueOf(40l))
                .color("RED")
                .quantity(10).build();

        val stockShoe3 = StockShoe.builder()
                .name("Puma")
                .size(BigInteger.valueOf(40l))
                .color("WHITE")
                .quantity(10).build();

        val stockShoes = Arrays.asList(stockShoe1, stockShoe2, stockShoe3);

        val stock = new Stock(FULL, stockShoes);

        stockService.updateStock(stock);

        verify(stockRepository, times(1)).deleteAll();
        verify(stockRepository, times(1)).saveAll(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailUpdateStockByMore30Packages(){
        val stockShoe1 = StockShoe.builder()
                .name("Nike SB")
                .size(BigInteger.valueOf(40l))
                .color("BLUE")
                .quantity(10).build();

        val stockShoe2 = StockShoe.builder()
                .name("Adidas")
                .size(BigInteger.valueOf(40l))
                .color("RED")
                .quantity(10).build();

        val stockShoe3 = StockShoe.builder()
                .name("Puma")
                .size(BigInteger.valueOf(40l))
                .color("WHITE")
                .quantity(11).build();

        val stockShoes = Arrays.asList(stockShoe1, stockShoe2, stockShoe3);

        val stock = new Stock(FULL, stockShoes);

        stockService.updateStock(stock);

        verify(stockRepository, never()).deleteAll();
        verify(stockRepository, never()).saveAll(any());
    }
}
