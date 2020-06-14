package com.bdpanajoto.shoppingcart.services.impl;

import com.bdpanajoto.shoppingcart.domain.dtos.ProductDTO;
import com.bdpanajoto.shoppingcart.repositories.ProductRepository;
import com.bdpanajoto.shoppingcart.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getProducts() {
        return this.productRepository.getProducts().stream().map(this::toProductDTO).collect(Collectors.toList());
    }

    private ProductDTO toProductDTO(ProductDTO product) {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice());
    }

    @Override
    public ProductDTO getById(String productId) {
        return this.productRepository.getById(productId);
    }

}
