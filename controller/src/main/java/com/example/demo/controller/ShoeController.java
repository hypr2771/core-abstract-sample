package com.example.demo.controller;

import com.example.demo.core.ShoeCore;
import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.out.Shoes;
import com.example.demo.facade.Facade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/shoes")
@RequiredArgsConstructor
public class ShoeController {

  private final Facade<ShoeCore> shoeFacade;

  @GetMapping(path = "/search")
  public ResponseEntity<Shoes> all(ShoeFilter filter, @RequestHeader Integer version){

    return ResponseEntity.ok(shoeFacade.get(version).search(filter));

  }

}
