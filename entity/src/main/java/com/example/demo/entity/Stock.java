package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private State state;

    @OneToMany
    @Size(max = 30)
    private List<ShoeWithQuantity> shoes;


    public enum State{

        EMPTY,
        FULL,
        SOME
    }

}
