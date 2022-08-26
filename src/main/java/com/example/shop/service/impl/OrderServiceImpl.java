package com.example.shop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.example.shop.entity.Order;
import com.example.shop.entity.OrderDetail;
import com.example.shop.entity.ShoppingCart;
import com.example.shop.entity.User;
import com.example.shop.repository.OrderDetailRepository;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.ShoppingCartRepository;
import com.example.shop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void saveOrder(User user) {
        Order order = new Order();
        order.setOrderStatus("PENDING");
        order.setOrderDate(new Date());
        order.setUser(user);
        
        List<OrderDetail> orderDetails = new ArrayList<>();
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUser(user);
        
        for(ShoppingCart cart : shoppingCarts){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cart.getProduct());
            orderDetail.setQuantity(cart.getQuantity());
            orderDetail.setUnitPrice(cart.getProduct().getCostPrice());
            orderDetailRepository.save(orderDetail);
            orderDetails.add(orderDetail);
        }

        order.setOrderDetailList(orderDetails);
        orderRepository.save(order);

    }
    
}
