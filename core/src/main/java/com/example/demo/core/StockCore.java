package com.example.demo.core;

import com.example.demo.dto.out.StockDTO;

public interface StockCore extends BaseCore{

  StockDTO getStock();

  void patchStock(StockDTO stockDTO);
}
