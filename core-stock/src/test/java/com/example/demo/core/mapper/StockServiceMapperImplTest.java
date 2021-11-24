package com.example.demo.core.mapper;

import com.example.demo.core.repository.entity.StockEntity;
import com.example.demo.core.repository.entity.StockId;
import com.example.demo.dto.common.StockShoe;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = StockServiceMapperImpl.class)
public class StockServiceMapperImplTest {

    @InjectMocks
    private StockServiceMapperImpl stockServiceMapper;

    @Test
    public void shouldSuccessToStockShoe(){
        val stockId = StockId.builder()
                .name("NikeSB")
                .size(BigInteger.valueOf(40l))
                .color("BLUE").build();
        val stock = StockEntity.builder()
                .stockId(stockId)
                .quantity(9).build();

        val stockShoe = stockServiceMapper.toStockShoe(stock);

        assertNotNull(stockShoe);
        assertEquals(stock.getQuantity(), stockShoe.getQuantity());
        assertEquals(stock.getStockId().getName(), stockShoe.getName());
        assertEquals(stock.getStockId().getSize(), stockShoe.getSize());
        assertEquals(stock.getStockId().getColor(), stockShoe.getColor());
    }

    @Test
    public void shouldSuccessToStock(){
        val stockShoe = StockShoe.builder()
                .name("Nike SB")
                .size(BigInteger.valueOf(40l))
                .color("BLUE")
                .quantity(10).build();

        val stock = stockServiceMapper.toStock(stockShoe);

        assertNotNull(stock);
        assertEquals(stockShoe.getQuantity(), stock.getQuantity());
        assertEquals(stockShoe.getName(), stock.getStockId().getName());
        assertEquals(stockShoe.getSize(), stock.getStockId().getSize());
        assertEquals(stockShoe.getColor(), stock.getStockId().getColor());
    }
}
