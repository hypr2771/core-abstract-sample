package com.example.demo.core;

import com.example.demo.dto.out.Stock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

public interface StockCore {

    Stock get();

    Stock patch(JsonPatch stock) throws JsonPatchException, JsonProcessingException;
}
