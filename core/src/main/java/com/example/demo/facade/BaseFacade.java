package com.example.demo.facade;

import com.example.demo.core.BaseCore;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseFacade<T extends BaseCore> {

    protected final Map<Integer, T> implementations = new HashMap<>();

    public void register(Integer version, T implementation){
        this.implementations.put(version, implementation);
    }
}
