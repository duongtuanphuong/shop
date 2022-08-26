package com.example.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shop.entity.Product;
import com.example.shop.entity.ShoppingCart;
import com.example.shop.entity.User;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    public List<ShoppingCart> findByUser(User user);

    public ShoppingCart findByUserAndProduct(User user,Product product);
}
