package com.bdpanajoto.shoppingcart.domain.dtos;

import java.util.Map;

public class CartDTO {
    private Map<String, Long> products;

    public CartDTO(Map<String, Long> products) {
        this.products = products;
    }

    public Map<String, Long> getProducts() {
        return products;
    }
}
