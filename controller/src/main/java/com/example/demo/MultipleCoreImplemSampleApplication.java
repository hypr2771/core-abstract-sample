package com.example.demo;

import com.example.demo.core.repository.StockRepository;
import com.example.demo.core.repository.entity.StockEntity;
import com.example.demo.core.repository.entity.StockId;
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

    StockEntity stock1 = new StockEntity(stockId1, 9);
    StockEntity stock2 = new StockEntity(stockId2, 10);
    StockEntity stock3 = new StockEntity(stockId3, 10);

    stockRepository.save(stock1);
    stockRepository.save(stock2);
    stockRepository.save(stock3);
  }
}
