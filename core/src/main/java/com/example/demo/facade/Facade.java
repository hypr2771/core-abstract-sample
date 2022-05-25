package com.example.demo.facade;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Facade<T> {


    private final Map<Integer, T> implementations = new HashMap<>();

    public T get(Integer version){
        return implementations.get(version);
    }

    public void register(Integer version, T implementation){
        this.implementations.put(version, implementation);
    }
}
