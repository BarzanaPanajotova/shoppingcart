package com.bdpanajoto.shoppingcart.domain.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class AddProductRequest {
    @NotBlank(message = "ProductId can not be null or blank!")
    private String productId;
    @Min(value = 1, message = "Count must be larger than 1")
    private long count;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
