package com.example.demo.services.impl;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Shoes;
import com.example.demo.dto.out.Stock;
import com.example.demo.entities.StockEntity;
import com.example.demo.mapper.StockMapper;
import com.example.demo.repositories.StockRepository;
import com.example.demo.services.IStockService;
import com.example.demo.services.exception.QuantityException;

@Service
@PropertySource("classpath:messages.properties")
public class StockServiceImpl implements IStockService {

	@Value("${message.capacity_max_30}")
	public String messageCapacity;

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
	public Stock updateStock(Stock stock) throws QuantityException {

		BigInteger totalQuantity = calculateQuantityShoes(stock.getShoes());

		if (totalQuantity.intValue() > 30) {
			throw new QuantityException(messageCapacity + totalQuantity.intValue(), 1);
		}

		StockEntity stockSaved = this.stockRepository.save(this.stockMapper.stockToStockEntity(stock));

		return this.stockMapper.stockEntityToStock(stockSaved);
	}

	/**
	 * Méthode permettant de calculer la somme totale des chaussures
	 * 
	 * @param shoes
	 * @return BigInteger
	 */
	private BigInteger calculateQuantityShoes(Shoes shoes) {
		BigInteger somme = BigInteger.valueOf(0);
		for (Shoe shoe : shoes.getShoes()) {
			somme = somme.add(shoe.getQuantity());
		}
		return somme;
	}

}
