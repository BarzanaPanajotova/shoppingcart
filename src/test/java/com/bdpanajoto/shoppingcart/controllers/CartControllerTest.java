package com.bdpanajoto.shoppingcart.controllers;

import com.bdpanajoto.shoppingcart.domain.dtos.AddProductRequest;
import com.bdpanajoto.shoppingcart.domain.dtos.CartDTO;
import com.bdpanajoto.shoppingcart.domain.dtos.RemoveProductRequest;
import com.bdpanajoto.shoppingcart.services.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
public class CartControllerTest extends AbstractControllerTest{

    @MockBean
    private CartService service;

    @Test
    public void shouldReturnCartFromService() throws Exception {
        Mockito.when(this.service.getCart()).thenReturn(new CartDTO(null));
        super.mvc.perform(get("/cart")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"products\":null}")));
    }

    @Test
    public void givenServiceThrowsException_shouldThrowInternalServerError() throws Exception {
        Mockito.when(this.service.getCart()).thenThrow(new RuntimeException());
        super.mvc.perform(get("/cart")).andDo(print()).andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldAddProductToCart() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setProductId("P001");
        addProductRequest.setCount(2);
        super.mvc.perform(post("/cart/add").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(addProductRequest))).andDo(print()).andExpect(status().isOk());
        Mockito.verify(this.service).addProduct("P001", 2);
    }

    @Test
    public void given0Count_shouldNotAddProductToCart() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setProductId("P001");
        addProductRequest.setCount(0);
        super.mvc.perform(post("/cart/add").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(addProductRequest))).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldRemoveProductFromCart() throws Exception {
        RemoveProductRequest removeProductRequest = new RemoveProductRequest();
        removeProductRequest.setProductId("P001");
        super.mvc.perform(patch("/cart/remove").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(removeProductRequest))).andDo(print()).andExpect(status().isOk());
        Mockito.verify(this.service).removeProduct("P001");
    }

    @Test
    public void shouldGetCartPrice() throws Exception {
        super.mvc.perform(get("/cart/price")).andDo(print()).andExpect(status().isOk());
        Mockito.verify(this.service).calculatePrice();
    }
}
