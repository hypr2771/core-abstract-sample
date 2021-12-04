package com.example.demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private State state;

    @OneToMany
    private List<ShoeWithQuantity> shoes;


    public enum State{

        EMPTY,
        FULL,
        SOME
    }

}
