# shoppingcart

As an end user of the shopping cart I would like to:
 * Add one or more products from the same or different types to the cart
 * Remove products from the shopping cart
 * If there are more than 10 product of the same type, their price should be discounted with 10%
 * If the price of the whole shopping cart exceeds 100000 (sum of all different product prices), total price should be discounted with 10%
 * Calculate and print the shopping cart price

Note: Implement only the business logic part. No need for UIs

Implement REST APIs for this requirements

API:
GET localhost:8080/cart/ - prints the contents of the cart

GET localhost:8080/cart/price - prints the total price of the cart

POST localhost:8080/cart/add - adds item to cart
Body:
```
{
    "productId":"P001",
    "count": 10
}
```

PATCH localhost:8080/cart/remove - removes 1 item from the cart - if there are 10 from the same type - 9 of them are left after this operaton
Body:
```
{
    "productId":"P001"
}
```

GET localhost:8080/products - prints the types of products and their prices
