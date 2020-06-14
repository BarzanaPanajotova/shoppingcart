package com.bdpanajoto.shoppingcart.services;

import com.bdpanajoto.shoppingcart.domain.dtos.CartDTO;
import com.bdpanajoto.shoppingcart.domain.dtos.ProductDTO;
import com.bdpanajoto.shoppingcart.repositories.CartRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTest {

    public static final String PRODUCT_ID = "P001";
    @MockBean
    private ProductService productService;

    @MockBean
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Test
    public void shouldReturnRepositoryCart() {
        Mockito.when(cartRepository.getCart()).thenReturn(new HashMap<>());

        CartDTO result = cartService.getCart();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.getProducts().isEmpty());
    }

    @Test
    public void shouldAddProduct() {
        ProductDTO productDTO = new ProductDTO();
        Mockito.when(productService.getById(PRODUCT_ID)).thenReturn(productDTO);

        cartService.addProduct(PRODUCT_ID, 2);

        Mockito.verify(cartRepository).addItem(productDTO, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddProduct() {
        Mockito.when(productService.getById("")).thenThrow(new IllegalArgumentException());

        cartService.addProduct("", 2);
    }

    @Test
    public void shouldRemoveProduct() {
        ProductDTO productDTO = new ProductDTO();
        Mockito.when(productService.getById(PRODUCT_ID)).thenReturn(productDTO);

        cartService.removeProduct(PRODUCT_ID);

        Mockito.verify(cartRepository).removeItem(productDTO);
    }

    @Test
    public void shouldCalculatePrice() {
        cartService.calculatePrice();

        Mockito.verify(cartRepository).calculatePrice();
    }
}
