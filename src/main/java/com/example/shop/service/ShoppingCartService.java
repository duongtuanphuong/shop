package com.example.shop.service;

import java.util.List;

import com.example.shop.entity.ShoppingCart;
import com.example.shop.entity.User;

public interface ShoppingCartService {
    List<ShoppingCart> listShoppingCart(User user);

    ShoppingCart addItemToCart(long productId,User user);

    void updateItemInCart(long productId,int quantity,User user);

    void deleteItemInCart(long productId,User user);

    void clearShoppingCart(User user);
}
