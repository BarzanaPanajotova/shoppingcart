package com.bdpanajoto.shoppingcart.services;

import com.bdpanajoto.shoppingcart.domain.dtos.CartDTO;

import java.math.BigDecimal;

public interface CartService {
    CartDTO getCart();

    void addProduct(String productId, long count);

    void removeProduct(String productId);

    BigDecimal calculatePrice();
}
