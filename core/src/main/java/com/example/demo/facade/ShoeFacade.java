package com.example.demo.facade;

import com.example.demo.core.ShoeCore;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ShoeFacade {

  private Map<BigInteger, ShoeCore> implementations = new HashMap<>();

  public ShoeCore get(BigInteger version){
    return implementations.get(version);
  }

  public void register(BigInteger version, ShoeCore implementation){
    this.implementations.put(version, implementation);
  }

}
