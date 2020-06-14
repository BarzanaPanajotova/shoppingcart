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

    public static final String CART_URL = "/cart";
    public static final String CART_ADD_URL = "/cart/add";
    public static final String CART_REMOVE_URL = "/cart/remove";
    public static final String CART_PRICE_URL = "/cart/price";
    public static final String PRODUCT_ID = "P001";

    @MockBean
    private CartService service;

    @Test
    public void shouldReturnCartFromService() throws Exception {
        Mockito.when(this.service.getCart()).thenReturn(new CartDTO(null));
        super.mvc.perform(get(CART_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"products\":null}")));
    }

    @Test
    public void givenServiceThrowsException_shouldThrowInternalServerError() throws Exception {
        Mockito.when(this.service.getCart()).thenThrow(new RuntimeException());
        super.mvc.perform(get(CART_URL)).andDo(print()).andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldAddProductToCart() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setProductId(PRODUCT_ID);
        addProductRequest.setCount(2);
        super.mvc.perform(post(CART_ADD_URL).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(addProductRequest))).andDo(print()).andExpect(status().isOk());
        Mockito.verify(this.service).addProduct(PRODUCT_ID, 2);
    }

    @Test
    public void given0Count_shouldNotAddProductToCart() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setProductId(PRODUCT_ID);
        addProductRequest.setCount(0);
        super.mvc.perform(post(CART_ADD_URL).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(addProductRequest))).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldRemoveProductFromCart() throws Exception {
        RemoveProductRequest removeProductRequest = new RemoveProductRequest();
        removeProductRequest.setProductId(PRODUCT_ID);
        super.mvc.perform(patch(CART_REMOVE_URL).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(removeProductRequest))).andDo(print()).andExpect(status().isOk());
        Mockito.verify(this.service).removeProduct(PRODUCT_ID);
    }

    @Test
    public void shouldGetCartPrice() throws Exception {
        super.mvc.perform(get(CART_PRICE_URL)).andDo(print()).andExpect(status().isOk());
        Mockito.verify(this.service).calculatePrice();
    }
}
