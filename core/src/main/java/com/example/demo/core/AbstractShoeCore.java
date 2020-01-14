package com.example.demo.core;

import com.example.demo.facade.ShoeFacade;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractShoeCore implements ShoeCore {

  @Autowired
  private ShoeFacade shoeFacade;

  @PostConstruct
  void init(){
    shoeFacade.register(getVersion(), this);
  }

  protected abstract BigInteger getVersion();

}
