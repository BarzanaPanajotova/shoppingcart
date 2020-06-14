package com.bdpanajoto.shoppingcart.repositories;

import com.bdpanajoto.shoppingcart.domain.dtos.ProductDTO;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class ProductRepository {

    private static final List<ProductDTO> PRODUCTS = Collections.unmodifiableList(
            Arrays.asList(
                    new ProductDTO("P001", "Bread", BigDecimal.valueOf(1.2)),
                    new ProductDTO("P002", "Orange juice 1L", BigDecimal.valueOf(3.6)),
                    new ProductDTO("P003", "Pork meat 1kg", BigDecimal.valueOf(10.6)),
                    new ProductDTO("P004", "Salmon fillet 1kg", BigDecimal.valueOf(32.48)),
                    new ProductDTO("P005", "Napkins Belana 100pc", BigDecimal.valueOf(4.24)),
                    new ProductDTO("P006", "Mayonnaise 200g", BigDecimal.valueOf(2.3))
            ));

    public List<ProductDTO> getProducts() {
        return PRODUCTS;
    }

    public ProductDTO getById(String productId) {
        return  PRODUCTS.stream().filter(x -> productId.equals(x.getId()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No shuch productId!"));
    }
}
