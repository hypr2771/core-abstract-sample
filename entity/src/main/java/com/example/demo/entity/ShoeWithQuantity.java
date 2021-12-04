package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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


    public enum Color{

        BLACK,
        BLUE,

    }
}
