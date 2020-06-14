package com.bdpanajoto.shoppingcart.domain.dtos;

import javax.validation.constraints.NotBlank;

public class RemoveProductRequest {
    @NotBlank
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
