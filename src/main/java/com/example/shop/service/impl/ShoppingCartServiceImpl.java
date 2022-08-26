package com.example.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop.entity.Product;
import com.example.shop.entity.ShoppingCart;
import com.example.shop.entity.User;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.ShoppingCartRepository;
import com.example.shop.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ShoppingCart> listShoppingCart(User user) {
        // TODO Auto-generated method stub
        return shoppingCartRepository.findByUser(user);
    }

    @Override
    public ShoppingCart addItemToCart(long productId, User user) {
        // TODO Auto-generated method stub
        Product product = productRepository.findById(productId).get();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserAndProduct(user, product);
        
        if(shoppingCart != null){
            shoppingCart.setQuantity(shoppingCart.getQuantity() + 1);
        }else{
            shoppingCart = new ShoppingCart();
            shoppingCart.setQuantity(1);
            shoppingCart.setProduct(product);
            shoppingCart.setUser(user);
        }

        shoppingCart.setTotalPrices(shoppingCart.getQuantity() * product.getCostPrice());

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void updateItemInCart(long productId, int quantity, User user) {
        // TODO Auto-generated method stub
        Product product = productRepository.findById(productId).get();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserAndProduct(user, product);
        shoppingCart.setQuantity(quantity);
        shoppingCart.setTotalPrices(product.getCostPrice() * shoppingCart.getQuantity());
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void deleteItemInCart(long productId, User user) {
        // TODO Auto-generated method stub
        Product product = productRepository.findById(productId).get();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserAndProduct(user, product);
        shoppingCartRepository.delete(shoppingCart);
    }

    @Override
    public void clearShoppingCart(User user) {
        // TODO Auto-generated method stub
        List<ShoppingCart> shoppingCarts = listShoppingCart(user);
        shoppingCartRepository.deleteAll(shoppingCarts);
    }
    
}
