package com.example.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.out.Stock;
import com.example.demo.entities.StockEntity;
import com.example.demo.mapper.StockMapper;
import com.example.demo.repositories.StockRepository;
import com.example.demo.services.IStockService;

@Service
public class StockServiceImpl implements IStockService {

	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private StockMapper stockMapper;
	
	@Override
	public Stock getStock() {
		
		StockEntity stockEntity = stockRepository.getCurrentStockWithShoes();
		
		return stockMapper.stockEntityToStock(stockEntity);
	}

	@Override
	public Stock updateStock(Stock stock) {
		
		StockEntity stockSaved = this.stockRepository.save(this.stockMapper.stockToStockEntity(stock));
		
		return this.stockMapper.stockEntityToStock(stockSaved);
	}
	

}
