package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigInteger;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@IdClass(ShoeWithQuantityId.class)
@Table(name = "shoes")
public class ShoeWithQuantity {

    @Id
    private BigInteger size;
    @Id
    private Color color;
    private BigInteger quantity;


    public enum Color{

        BLACK,
        BLUE,

    }
}
