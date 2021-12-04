package com.example.demo.core;

import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Shoes;
import com.example.demo.entity.ShoeWithQuantity;

import java.math.BigInteger;
import java.util.List;

@Implementation(version = 2)
public class ShoeCoreNew extends AbstractShoeCore {

    @Override
    public Shoes search(final ShoeFilter filter) {
        return Shoes.builder()
                .shoes(List.of(Shoe.builder()
                        .name("New shoe")
                        .color(filter.getColor().orElse(ShoeWithQuantity.Color.BLACK))
                        .size(filter.getSize().orElse(BigInteger.TWO))
                        .build()))
                .build();
    }
}