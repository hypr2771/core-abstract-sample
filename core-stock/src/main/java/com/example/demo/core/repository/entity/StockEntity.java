package com.example.demo.core.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Value
@Data
@AllArgsConstructor
@Document(collection = "stock")
public class StockEntity {

    @Id
    private StockId stockId;
    private Integer quantity;
}
