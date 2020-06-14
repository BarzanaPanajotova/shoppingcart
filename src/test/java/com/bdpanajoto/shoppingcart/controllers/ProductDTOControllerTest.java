package com.bdpanajoto.shoppingcart.controllers;

import com.bdpanajoto.shoppingcart.domain.dtos.ProductDTO;
import com.bdpanajoto.shoppingcart.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductDTOControllerTest extends AbstractControllerTest{

    private static final List<ProductDTO> PRODUCTS = Collections.unmodifiableList(
            Arrays.asList(
                    new ProductDTO("P001", "Bread", BigDecimal.valueOf(1.2)),
                    new ProductDTO("P002", "Orange juice", BigDecimal.valueOf(3.6))));
    public static final String PRODUCTS_RESPONSE = "[{\"id\":\"P001\",\"name\":\"Bread\",\"price\":1.2},{\"id\":\"P002\",\"name\":\"Orange juice\",\"price\":3.6}]";
    public static final String URL_TEMPLATE = "/products";

    @MockBean
    private ProductService service;

    @Test
    public void shouldReturnProductsFromService() throws Exception {
        when(this.service.getProducts()).thenReturn(PRODUCTS);
        super.mvc.perform(get(URL_TEMPLATE)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo(PRODUCTS_RESPONSE)));
    }

    @Test
    public void givenEmptyListFromService_shouldReturnEmptyList() throws Exception {
        when(this.service.getProducts()).thenReturn(null);
        super.mvc.perform(get(URL_TEMPLATE)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo("")));
    }

    @Test
    public void givenServiceThrowsException_shouldThrowInternalServerError() throws Exception {
        when(this.service.getProducts()).thenThrow(new RuntimeException());
        super.mvc.perform(get(URL_TEMPLATE)).andDo(print()).andExpect(status().isInternalServerError());
    }
}
