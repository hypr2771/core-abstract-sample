package com.example.demo.controller;

import com.example.demo.core.ShoeCoreLegacy;
import com.example.demo.core.ShoeCoreNew;
import com.example.demo.facade.ShoeFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
public abstract class AbstractShoeTest {

    protected static final String VERSION = "version";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper jsonMapper;

    @Autowired
    private ShoeFacade shoeFacade;

    @Before
    public void setUp() {
        shoeFacade.register(1, new ShoeCoreNew());
        shoeFacade.register(2, new ShoeCoreLegacy());
    }
}
