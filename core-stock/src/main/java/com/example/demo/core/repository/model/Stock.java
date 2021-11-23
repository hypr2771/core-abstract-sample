package com.example.demo.core.repository.model;

import lombok.Data;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Data
@Document(collection = "stock")
public class Stock {

    @Id
    private StockId stockId;
    private Integer quantity;

    public Stock(StockId stockId, Integer quantity) {
        this.stockId = stockId;
        this.quantity = quantity;
    }

    public StockId getStockId() {
        return stockId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
