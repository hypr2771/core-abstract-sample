package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Setter(AccessLevel.NONE)
    private State state = State.EMPTY;

    @OneToMany
    @Size(max = 30)
    private List<ShoeWithQuantity> shoes = new ArrayList<>();

    // Work around to have default value when using builder
    public static class StockBuilder {
        private List<ShoeWithQuantity> shoes = new ArrayList<>();
    }

    @Builder(builderClassName = "StockBuilder")
    public Stock(List<ShoeWithQuantity> shoes) {
        this.shoes = shoes;
    }

    public enum State {
        EMPTY,
        FULL,
        SOME
    }


    @PrePersist
    @PreUpdate
    public void updateStockState() {
        switch (shoes.size()) {
            case 0:
                state = State.EMPTY;
                break;
            case 30:
                state = State.FULL;
                break;
            default:
                state = State.SOME;
        }

    }
}
