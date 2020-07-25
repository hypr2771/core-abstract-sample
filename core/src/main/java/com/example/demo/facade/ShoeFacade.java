package com.example.demo.facade;

import com.example.demo.core.ShoeCore;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ShoeFacade {

  private final Map<Integer, ShoeCore> implementations = new HashMap<>();

  public ShoeCore get(Integer version){
    return implementations.get(version);
  }

  public void register(Integer version, ShoeCore implementation){
    this.implementations.put(version, implementation);
  }

}
