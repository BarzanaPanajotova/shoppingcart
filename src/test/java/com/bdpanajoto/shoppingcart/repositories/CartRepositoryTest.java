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
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.ONE);

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
        ProductDTO product = new ProductDTO();
        product.setName("REM");
        product.setPrice(BigDecimal.ONE);
        cartRepository.addItem(product, 1);
        Map<ProductDTO, Long> cart = cartRepository.getCart();
        Assert.assertTrue(cart.containsKey(product));

        cartRepository.removeItem(product);

        Assert.assertNotNull(cart);
        Assert.assertTrue(!cart.containsKey(product));
    }

    @Test
    public void givenTenItems_shouldCalculatePriceWithDiscount() {
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.TEN);
        cartRepository.addItem(product, 10);

        Assert.assertEquals(BigDecimal.valueOf(90).setScale(2), cartRepository.calculatePrice());
    }

    @Test
    public void givenPriceLargerThan100000_shouldCalculatePriceWithDiscount() {
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.valueOf(100001));
        cartRepository.addItem(product, 1);

        ProductDTO second = new ProductDTO();
        second.setPrice(BigDecimal.valueOf(20.6));
        cartRepository.addItem(second, 3);

        Assert.assertEquals(BigDecimal.valueOf(90056.52).setScale(2), cartRepository.calculatePrice());
    }

    @Test
    public void givenTenItemsAndPriceLargerThan100000_shouldCalculatePriceWithDiscount() {
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.valueOf(100001));
        cartRepository.addItem(product, 10);

        Assert.assertEquals(BigDecimal.valueOf(810008.10).setScale(2), cartRepository.calculatePrice());
    }
}
