package com.bdpanajoto.shoppingcart.services;

import com.bdpanajoto.shoppingcart.domain.dtos.ProductDTO;
import com.bdpanajoto.shoppingcart.repositories.ProductRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    public static final String PRODUCT_ID = "P001";
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    public void shouldReturnRepositoryProducts() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(PRODUCT_ID);
        productDTO.setPrice(BigDecimal.TEN);
        List<ProductDTO> list = Arrays.asList(productDTO);
        Mockito.when(productRepository.getProducts()).thenReturn(list);

        List<ProductDTO> products = productService.getProducts();

        Assert.assertNotNull(products);
        Assert.assertTrue(!products.isEmpty());
        Assert.assertEquals(productDTO, products.get(0));
    }

    @Test
    public void shouldReturnProductFromRepositoryById(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(PRODUCT_ID);
        productDTO.setPrice(BigDecimal.TEN);
        Mockito.when(productRepository.getById(PRODUCT_ID)).thenReturn(productDTO);

        ProductDTO result = productService.getById(PRODUCT_ID);

        Assert.assertNotNull(result);
        Assert.assertEquals(productDTO, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenEmptyId_shouldNotReturnProductFromRepositoryById(){
        Mockito.when(productRepository.getById("")).thenThrow(new IllegalArgumentException());

        ProductDTO result = productService.getById("");

        Assert.assertNotNull(result);
    }
}
