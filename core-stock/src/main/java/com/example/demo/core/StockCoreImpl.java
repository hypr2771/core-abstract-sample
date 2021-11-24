package com.example.demo.core;

import com.example.demo.core.service.StockService;
import com.example.demo.dto.out.Stock;
import org.springframework.beans.factory.annotation.Autowired;


@Implementation(version = 1)
public class StockCoreImpl extends AbstractStockCore {

  @Autowired
  private StockService stockService;

  @Override
  public Stock getStock() {
    return stockService.getStock();
  }

  @Override
  public void updateStock(Stock stock) {
    stockService.updateStock(stock);
  }
}
