package com.example.demo.facade;

import com.example.demo.core.ShoeCore;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ShoeFacade extends BaseFacade<ShoeCore>{

  public ShoeCore get(Integer version){
    return implementations.get(version);
  }
}
