package com.bdpanajoto.shoppingcart.controllers;

import com.bdpanajoto.shoppingcart.domain.dtos.AddProductRequest;
import com.bdpanajoto.shoppingcart.domain.dtos.CartDTO;
import com.bdpanajoto.shoppingcart.domain.dtos.RemoveProductRequest;
import com.bdpanajoto.shoppingcart.services.CartService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDTO getCart() {
        return this.cartService.getCart();
    }

    @PostMapping(value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addProductToCart(@Valid @RequestBody AddProductRequest addProductRequest) {
        this.cartService.addProduct(addProductRequest.getProductId(), addProductRequest.getCount());
    }

    @PatchMapping(value = "/remove",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeProductFromCart(@Valid @RequestBody RemoveProductRequest removeProductRequest) {
        this.cartService.removeProduct(removeProductRequest.getProductId());
    }

    @GetMapping(value = "/price",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,BigDecimal> getPrice() {
        return Collections.singletonMap("totalPrice", this.cartService.calculatePrice());
    }
}
