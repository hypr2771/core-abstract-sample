package com.example.demo.fixture;

import com.example.demo.dto.in.ShoeFilter.Color;
import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Shoes;

import java.math.BigInteger;
import java.util.Arrays;

public class ShoesFixture {

    public static Shoes getShoesExisting() { // TODO H2 and Liquibase versionning by csv
        Shoe shoe = Shoe.builder()
                .name("New shoe")
                .size(BigInteger.TWO)
                .color(Color.BLACK)
                .build();
        return Shoes.builder()
                .shoes(Arrays.asList(shoe))
                .build();
    }
}
