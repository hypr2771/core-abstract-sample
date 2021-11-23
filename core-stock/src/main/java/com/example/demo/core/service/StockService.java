package com.example.demo.core.service;

import com.example.demo.dto.common.StockShoe;
import com.example.demo.dto.out.StockDTO;

import java.util.List;

public interface StockService {

    StockDTO getStock();

    void patchStock(StockDTO stockDTO);
}
