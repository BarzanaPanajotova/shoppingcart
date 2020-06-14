package com.bdpanajoto.shoppingcart.services.impl;

import com.bdpanajoto.shoppingcart.domain.dtos.CartDTO;
import com.bdpanajoto.shoppingcart.domain.dtos.ProductDTO;
import com.bdpanajoto.shoppingcart.repositories.CartRepository;
import com.bdpanajoto.shoppingcart.services.CartService;
import com.bdpanajoto.shoppingcart.services.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    public CartDTO getCart() {
        return new CartDTO(
                cartRepository.getCart().entrySet().stream()
                        .collect(Collectors.toMap(key -> key.getKey().getId(), value -> value.getValue())));
    }

    @Override
    public void addProduct(String productId, long count) {
        ProductDTO productDTO = productService.getById(productId);
        cartRepository.addItem(productDTO, count);
    }

    @Override
    public void removeProduct(String productId) {
        ProductDTO productDTO = productService.getById(productId);
        cartRepository.removeItem(productDTO);
    }

    @Override
    public BigDecimal calculatePrice() {
        return cartRepository.calculatePrice();
    }
}
