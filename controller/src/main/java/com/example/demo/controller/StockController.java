package com.example.demo.controller;

import com.example.demo.controller.api.StockApi;
import com.example.demo.controller.rdto.StockRDTO;
import com.example.demo.facade.StockFacade;
import com.example.demo.mapper.StockControllerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class StockController implements StockApi {

  private final StockFacade stockFacade;
  private final StockControllerMapper stockControllerMapper;

  @Override
  public ResponseEntity<StockRDTO> getStock(Integer version) {
    StockRDTO stockRDTO = stockControllerMapper.toStockRDTO(stockFacade.get(version).getStock());
    return ResponseEntity.ok(stockRDTO);
  }

  @Override
  public ResponseEntity<Void> updateStock(@Valid StockRDTO body, Integer version) {
    stockFacade.get(version).updateStock(stockControllerMapper.toStock(body));
    return ResponseEntity.ok().build();
  }
}
