package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "shoes")
public class ShoeWithQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private BigInteger size;
    private Color color;
    private BigInteger quantity;

    public ShoeWithQuantity(BigInteger size, Color color, BigInteger quantity) {
        this.size = size;
        this.color = color;
        this.quantity = quantity;

    }

    public ShoeWithQuantity() {

    }

    public enum Color{

        BLACK,
        BLUE,

    }
}
