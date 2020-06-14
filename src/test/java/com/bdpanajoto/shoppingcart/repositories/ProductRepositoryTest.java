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

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void shouldListAllProducts(){
        List<ProductDTO> list = productRepository.getProducts();

        Assert.assertNotNull(list);
        Assert.assertEquals(3, list.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException(){
        productRepository.getById("");
    }

    @Test
    public void shouldGetProduct(){
        ProductDTO productDTO = productRepository.getById("P001");

        Assert.assertNotNull(productDTO);
        Assert.assertEquals("P001", productDTO.getId());
    }
}
