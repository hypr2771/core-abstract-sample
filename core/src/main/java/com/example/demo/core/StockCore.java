package com.example.demo.core;

import com.example.demo.dto.in.UpdatedStock;
import com.example.demo.dto.out.StockState;

public interface StockCore {

    StockState getStockState();
    StockState updateStock(UpdatedStock updatedStock);
}
