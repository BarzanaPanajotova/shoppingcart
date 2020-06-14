package com.bdpanajoto.shoppingcart.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public abstract class AbstractControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    String asJsonString(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}