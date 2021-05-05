package com.example.demo.services;

import com.example.demo.dto.out.Stock;
import com.example.demo.services.exception.QuantityException;

public interface IStockService {
	
	/**
	 * Méthode permettant de retourner le stock et son état
	 * @return Stock
	 */
	Stock getStock();

	/**
	 * Méthode permettant de mettre à jour le stock
	 * 
	 * @param stock
	 * @return Stock
	 * @throws QuantityException 
	 */
	Stock updateStock(Stock stock) throws QuantityException;

}
