package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    private State state = State.EMPTY;

    @OneToMany
    @Size(max = 30)
    private List<ShoeWithQuantity> shoes = new ArrayList<>();

    // Work around to have default value when using builder
    public static class StockBuilder {
        private State state = State.EMPTY;
        private List<ShoeWithQuantity> shoes = new ArrayList<>();
    }

    @Builder(builderClassName = "StockBuilder")
    public Stock(State state, List<ShoeWithQuantity> shoes) {
        this.state = state;
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
