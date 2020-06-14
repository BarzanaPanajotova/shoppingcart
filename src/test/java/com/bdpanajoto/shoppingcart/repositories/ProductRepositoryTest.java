package com.bdpanajoto.shoppingcart.repositories;

import com.bdpanajoto.shoppingcart.domain.dtos.ProductDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    public static final String PRODUCT_ID = "P001";
    public static final int EXPECTED_PRODUCT_COUNT = 6;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void shouldListAllProducts() {
        List<ProductDTO> list = productRepository.getProducts();

        Assert.assertNotNull(list);
        Assert.assertEquals(EXPECTED_PRODUCT_COUNT, list.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() {
        productRepository.getById("");
    }

    @Test
    public void shouldGetProduct() {
        ProductDTO productDTO = productRepository.getById(PRODUCT_ID);

        Assert.assertNotNull(productDTO);
        Assert.assertEquals(PRODUCT_ID, productDTO.getId());
    }
}
