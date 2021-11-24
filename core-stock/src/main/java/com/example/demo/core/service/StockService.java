package com.example.demo.core.service;

import com.example.demo.dto.out.Stock;

public interface StockService {

    Stock getStock();

    void updateStock(Stock stock);
}
