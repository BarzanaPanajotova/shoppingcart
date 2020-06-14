package com.bdpanajoto.shoppingcart.repositories;

import com.bdpanajoto.shoppingcart.domain.dtos.ProductDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Before
    public void setup() {
        Map<ProductDTO, Long> cart = cartRepository.getCart();
        cart.clear();
    }

    @Test
    public void shouldReturnCart() {
        Map<ProductDTO, Long> cart = cartRepository.getCart();

        Assert.assertNotNull(cart);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddItem() {
        cartRepository.addItem(null, 1);
    }

    @Test
    public void shouldAddItem() {
        ProductDTO product = getProductDTO();

        cartRepository.addItem(product, 1);
        cartRepository.addItem(product, 3);

        Map<ProductDTO, Long> cart = cartRepository.getCart();
        Assert.assertNotNull(cart);
        Assert.assertTrue(cart.containsKey(product));
        Assert.assertEquals(4, cart.get(product).longValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAttemptRemoveItem() {
        cartRepository.removeItem(null);
    }

    @Test
    public void shouldRemoveItem() {
        ProductDTO product = getProductDTO();
        cartRepository.addItem(product, 1);
        Map<ProductDTO, Long> cart = cartRepository.getCart();
        Assert.assertTrue(cart.containsKey(product));

        cartRepository.removeItem(product);

        Assert.assertNotNull(cart);
        Assert.assertTrue(!cart.containsKey(product));
    }

    @Test
    public void givenTenItems_shouldCalculatePriceWithDiscount() {
        ProductDTO product = getProductDTO();
        product.setPrice(BigDecimal.TEN);
        cartRepository.addItem(product, 10);

        BigDecimal result = cartRepository.calculatePrice();

        Assert.assertEquals(BigDecimal.valueOf(90).setScale(2), result);
    }

    @Test
    public void givenPriceLargerThan100000_shouldCalculatePriceWithDiscount() {
        ProductDTO product = getProductDTO();
        product.setPrice(BigDecimal.valueOf(100001));
        cartRepository.addItem(product, 1);
        ProductDTO second = getProductDTO();
        second.setPrice(BigDecimal.valueOf(20.6));
        cartRepository.addItem(second, 3);

        BigDecimal result = cartRepository.calculatePrice();

        Assert.assertEquals(BigDecimal.valueOf(90056.52).setScale(2), result);
    }

    @Test
    public void givenTenItemsAndPriceLargerThan100000_shouldCalculatePriceWithDiscount() {
        ProductDTO product = getProductDTO();
        product.setPrice(BigDecimal.valueOf(100001));
        cartRepository.addItem(product, 10);

        BigDecimal result = cartRepository.calculatePrice();

        Assert.assertEquals(BigDecimal.valueOf(810008.10).setScale(2), result);
    }

    private ProductDTO getProductDTO() {
        ProductDTO product = new ProductDTO();
        product.setId("P001");
        product.setName("Milk");
        product.setPrice(BigDecimal.ONE);
        return product;
    }
}
