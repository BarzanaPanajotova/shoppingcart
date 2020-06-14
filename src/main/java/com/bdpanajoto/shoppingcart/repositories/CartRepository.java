package com.bdpanajoto.shoppingcart.repositories;

import com.bdpanajoto.shoppingcart.domain.dtos.ProductDTO;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CartRepository {
    public static final BigDecimal NUMBER_OF_ITEMS_ELIGIBLE_FOR_DISCOUNT = BigDecimal.TEN;
    public static final BigDecimal TOTAL_VALUE_ELIGIBLE_FOR_DISCOUNT = BigDecimal.valueOf(100000);
    public static final BigDecimal DISCOUNT = BigDecimal.valueOf(0.9);
    public static final int PRICE_SCALE = 2;
    public static final String PRODUCT_CAN_NOT_BE_NULL_EXCEPTION = "Product can not be NULL";

    private final Map<ProductDTO, Long> cart;

    public CartRepository(){
        cart = Collections.synchronizedMap(new HashMap<>());
    }

    public Map<ProductDTO, Long> getCart() {
        return cart;
    }

    public void addItem(ProductDTO product, long count) {
        if (product == null) throw new IllegalArgumentException(PRODUCT_CAN_NOT_BE_NULL_EXCEPTION);

        if (cart.containsKey(product)) {
            count = count + cart.get(product);
        }
        cart.put(product, count);
    }

    public void removeItem(ProductDTO product) {
        if (product == null) throw new IllegalArgumentException(PRODUCT_CAN_NOT_BE_NULL_EXCEPTION);
        if (cart.containsKey(product)) {
            cart.put(product, cart.get(product) - 1);

            if (cart.get(product) == 0) {
                cart.remove(product);
            }
        }
    }

    public BigDecimal calculatePrice() {
        BigDecimal totalPrice = cart.entrySet().stream()
                .map(entry -> calculateItemPrice(BigDecimal.valueOf(entry.getValue()),
                        entry.getKey().getPrice()))
                .reduce((a, b) -> a.add(b))
                .orElse(BigDecimal.ZERO);

        return applyDiscount(totalPrice, TOTAL_VALUE_ELIGIBLE_FOR_DISCOUNT).setScale(PRICE_SCALE);
    }

    private BigDecimal calculateItemPrice(BigDecimal numberOfItems, BigDecimal itemPrice) {
        BigDecimal price = itemPrice.multiply(numberOfItems);
        return applyDiscount(price, NUMBER_OF_ITEMS_ELIGIBLE_FOR_DISCOUNT);
    }

    private BigDecimal applyDiscount(BigDecimal totalPrice, BigDecimal edge) {
        if (edge.compareTo(totalPrice) <= 0) {
            return totalPrice.multiply(DISCOUNT);
        }

        return totalPrice;
    }
}
