package com.example.demo.core;

import com.example.demo.dto.out.Stock;

public interface StockCore extends BaseCore{

  Stock getStock();

  void updateStock(Stock stock);
}
