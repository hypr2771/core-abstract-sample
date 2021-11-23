package com.example.demo.facade;

import com.example.demo.core.StockCore;
import org.springframework.stereotype.Component;

@Component
public class StockFacade extends BaseFacade<StockCore>{

  public StockCore get(Integer version){
    return implementations.get(version);
  }
}
