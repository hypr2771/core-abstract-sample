package com.example.demo;

import com.example.demo.core.repository.StockRepository;
import com.example.demo.core.repository.entity.StockEntity;
import com.example.demo.core.repository.entity.StockId;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;
import java.util.Arrays;

@SpringBootApplication
public class MultipleCoreImplemSampleApplication implements CommandLineRunner {

  @Autowired
  private StockRepository stockRepository;

  public static void main(String[] args) {
    SpringApplication.run(MultipleCoreImplemSampleApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    val stockId1 = new StockId("Nike SB", BigInteger.valueOf(40l), "BLUE");
    val stockId2 = new StockId("Adidas", BigInteger.valueOf(40l), "RED");
    val stockId3 = new StockId("Puma", BigInteger.valueOf(40l), "WHITE");

    val stock1 = new StockEntity(stockId1, 9);
    val stock2 = new StockEntity(stockId2, 10);
    val stock3 = new StockEntity(stockId3, 10);

    stockRepository.saveAll(Arrays.asList(stock1, stock2, stock3));
  }
}
