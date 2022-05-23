package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "SHOE")
public class ShoeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String name;

    @Column
    @PositiveOrZero
    @NotNull
    private Integer size;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column
    @PositiveOrZero
    @Max(30)
    @NotNull
    private Integer quantity;
}

