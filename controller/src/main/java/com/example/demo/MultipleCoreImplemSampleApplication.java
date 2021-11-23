package com.example.demo;

import com.example.demo.core.repository.StockRepository;
import com.example.demo.core.repository.model.StockModel;
import com.example.demo.core.repository.model.StockId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;

@SpringBootApplication
public class MultipleCoreImplemSampleApplication implements CommandLineRunner {

  @Autowired
  private StockRepository stockRepository;

  public static void main(String[] args) {
    SpringApplication.run(MultipleCoreImplemSampleApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    StockId stockId1 = new StockId("Nike SB", BigInteger.valueOf(40l), "BLUE");
    StockId stockId2 = new StockId("Adidas", BigInteger.valueOf(40l), "RED");
    StockId stockId3 = new StockId("Puma", BigInteger.valueOf(40l), "WHITE");

    StockModel stock1 = new StockModel(stockId1, 9);
    StockModel stock2 = new StockModel(stockId2, 10);
    StockModel stock3 = new StockModel(stockId3, 10);

    stockRepository.save(stock1);
    stockRepository.save(stock2);
    stockRepository.save(stock3);
  }
}
