package com.bdpanajoto.shoppingcart.controllers;

import com.bdpanajoto.shoppingcart.domain.dtos.ProductDTO;
import com.bdpanajoto.shoppingcart.services.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> listProducts() {
        return this.productService.getProducts();
    }
}
