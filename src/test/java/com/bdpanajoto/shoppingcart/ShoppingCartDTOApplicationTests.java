package com.bdpanajoto.shoppingcart;

import com.bdpanajoto.shoppingcart.controllers.CartController;
import com.bdpanajoto.shoppingcart.controllers.ProductController;
import com.bdpanajoto.shoppingcart.services.CartService;
import com.bdpanajoto.shoppingcart.services.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShoppingCartDTOApplicationTests {

    @Autowired
    public CartController cartController;

    @Autowired
    public ProductController productController;

    @Autowired
    public CartService cartService;

    @Autowired
    public ProductService productService;

    @Test
    void contextLoads() {
        Assertions.assertThat(cartController).isNotNull();
        Assertions.assertThat(productController).isNotNull();
        Assertions.assertThat(cartService).isNotNull();
        Assertions.assertThat(productService).isNotNull();
    }

}
