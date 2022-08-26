package com.example.shop.service;

import com.example.shop.entity.ShoppingCart;
import com.example.shop.entity.User;

public interface OrderService {
    void saveOrder(User user);
}
