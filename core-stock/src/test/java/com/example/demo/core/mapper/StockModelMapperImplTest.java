package com.example.demo.core.mapper;

import com.example.demo.core.repository.model.StockId;
import com.example.demo.core.repository.model.StockModel;
import com.example.demo.dto.common.StockShoe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = StockMapperImpl.class)
public class StockModelMapperImplTest {

    @InjectMocks
    private StockMapperImpl stockMapper;

    @Test
    public void shouldSuccessToStockShoe(){
        StockId stockId = new StockId("Nike SB", BigInteger.valueOf(40l), "BLUE");
        StockModel stock = new StockModel(stockId, 9);

        StockShoe stockShoe = stockMapper.toStockShoe(stock);

        assertNotNull(stockShoe);
        assertEquals(stock.getQuantity(), stockShoe.getQuantity());
        assertEquals(stock.getStockId().getName(), stockShoe.getName());
        assertEquals(stock.getStockId().getSize(), stockShoe.getSize());
        assertEquals(stock.getStockId().getColor(), stockShoe.getColor());
    }

    @Test
    public void shouldSuccessToStock(){
        StockShoe stockShoe = new StockShoe("Nike SB", BigInteger.valueOf(40l), "BLUE", 10);

        StockModel stock = stockMapper.toStock(stockShoe);

        assertNotNull(stock);
        assertEquals(stockShoe.getQuantity(), stock.getQuantity());
        assertEquals(stockShoe.getName(), stock.getStockId().getName());
        assertEquals(stockShoe.getSize(), stock.getStockId().getSize());
        assertEquals(stockShoe.getColor(), stock.getStockId().getColor());
    }
}
