package com.example.demo.core.service;

import com.example.demo.core.mapper.StockMapperImpl;
import com.example.demo.core.repository.StockRepository;
import com.example.demo.core.repository.model.StockId;
import com.example.demo.core.repository.model.StockModel;
import com.example.demo.dto.common.StockShoe;
import com.example.demo.dto.out.Stock;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
@SpringBootTest(classes = StockServiceLegacyImpl.class)
public class StockModelServiceLegacyImplTest {

    @InjectMocks
    private StockServiceLegacyImpl stockServiceLegacy;

    @Mock
    private StockRepository stockRepository;

    @Spy
    private StockMapperImpl stockMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSuccessGetStock(){
        StockId stockId = new StockId("Nike SB", BigInteger.valueOf(40l), "BLUE");
        StockModel stock = new StockModel(stockId, 9);

        List<StockModel> stockList = new ArrayList<>();
        stockList.add(stock);

        when(stockRepository.findAll()).thenReturn(stockList);

        Stock stockDTO = stockServiceLegacy.getStock();

        assertNotNull(stockDTO);
        assertEquals(stock.getStockId().getName(), stockDTO.getShoes().stream().findFirst().get().getName());
        assertEquals(stock.getStockId().getColor(), stockDTO.getShoes().stream().findFirst().get().getColor());
        assertEquals(stock.getStockId().getSize(), stockDTO.getShoes().stream().findFirst().get().getSize());
        assertEquals(stock.getQuantity(), stockDTO.getShoes().stream().findFirst().get().getQuantity());
        assertEquals(SOME, stockDTO.getState());
    }

    @Test
    public void shouldSuccessPatchStock(){
        StockShoe stockShoe1 = new StockShoe("Nike SB", BigInteger.valueOf(40l), "BLUE", 10);
        StockShoe stockShoe2 = new StockShoe("Adidas", BigInteger.valueOf(40l), "RED", 10);
        StockShoe stockShoe3 = new StockShoe("Puma", BigInteger.valueOf(40l), "WHITE", 10);

        List<StockShoe> stockShoes = Arrays.asList(stockShoe1, stockShoe2, stockShoe3);

        Stock stock = new Stock(FULL, stockShoes);

        stockServiceLegacy.patchStock(stock);

        verify(stockRepository, times(1)).deleteAll();
        verify(stockRepository, times(1)).saveAll(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailPatchStockByMore30Packages(){
        StockShoe stockShoe1 = new StockShoe("Nike SB", BigInteger.valueOf(40l), "BLUE", 10);
        StockShoe stockShoe2 = new StockShoe("Adidas", BigInteger.valueOf(40l), "RED", 10);
        StockShoe stockShoe3 = new StockShoe("Puma", BigInteger.valueOf(40l), "WHITE", 11);

        List<StockShoe> stockShoes = Arrays.asList(stockShoe1, stockShoe2, stockShoe3);

        Stock stock = new Stock(FULL, stockShoes);

        stockServiceLegacy.patchStock(stock);

        verify(stockRepository, never()).deleteAll();
        verify(stockRepository, never()).saveAll(any());
    }
}
