package com.bdpanajoto.shoppingcart.services;

import com.bdpanajoto.shoppingcart.domain.dtos.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getProducts();

    ProductDTO getById(String productId);
}
