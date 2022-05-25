package com.example.demo.core;

import java.util.Optional;
import javax.annotation.PostConstruct;

import com.example.demo.facade.Facade;
import lombok.val;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractShoeCore implements ShoeCore {

  @Autowired
  private Facade<ShoeCore> shoeFacade;

  @PostConstruct
  void init(){

    val version = Optional.ofNullable(this.getClass().getAnnotation(Implementation.class))
                          .map(Implementation::version)
                          .orElseThrow(() -> new FatalBeanException("AbstractShoeCore implementation should be annotated with @Implementation"));

    shoeFacade.register(version, this);

  }

}
