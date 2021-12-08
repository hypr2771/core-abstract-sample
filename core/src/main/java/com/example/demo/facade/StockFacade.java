package com.example.demo.facade;

import com.example.demo.core.StockCore;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StockFacade {
    private final Map<Integer, StockCore> implementations = new HashMap<>();

    public StockCore get(Integer version) {
        return implementations.get(version);
    }

    public void register(Integer version, StockCore implementation) {
        this.implementations.put(version, implementation);
    }

}
