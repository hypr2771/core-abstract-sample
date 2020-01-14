package com.example.demo.core;

import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.out.Shoes;

public interface ShoeCore {

  Shoes search(ShoeFilter filter);

}
