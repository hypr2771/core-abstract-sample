package com.example.demo.core;

import com.example.demo.core.service.StockService;
import com.example.demo.dto.out.StockDTO;
import org.springframework.beans.factory.annotation.Autowired;


@Implementation(version = 1)
public class StockCoreLegacy extends AbstractStockCore {

  @Autowired
  private StockService stockService;

  @Override
  public StockDTO getStock() {
    return stockService.getStock();
  }

  @Override
  public void patchStock(StockDTO stockDTO) {
    stockService.patchStock(stockDTO);
  }
}
