package com.example.demo.dto.in;

import java.awt.Color;
import java.math.BigInteger;
import lombok.RequiredArgsConstructor;

public class ShoeFilter {

  public BigInteger size;
  public Color color;

  public enum Color{

    BLACK,
    BLUE,
    ;

  }

}
