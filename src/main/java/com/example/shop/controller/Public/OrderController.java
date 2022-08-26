package com.example.shop.controller.Public;

import java.security.Principal;
import java.util.List;

import org.jboss.jandex.TypeTarget.Usage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.shop.entity.Order;
import com.example.shop.entity.ShoppingCart;
import com.example.shop.entity.User;
import com.example.shop.service.LoginService;
import com.example.shop.service.OrderService;
import com.example.shop.service.ShoppingCartService;

@Controller
public class OrderController {
    
    @Autowired
    private LoginService loginService;

    @Autowired
    private ShoppingCartService shoppingCartService;
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/shop/checkout")
    public String checkout(Model model,Principal principal){
        if(principal == null){
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = loginService.findByUsername(username);

        if(user.getPhoneNumber()== null || user.getAddress() == null){
            model.addAttribute("user", user);
            model.addAttribute("error", "You must fill the information before checkout!!");
            return "/shop/account";
        }
        else{
            model.addAttribute("user", user);
            List<ShoppingCart> shoppingCarts = shoppingCartService.listShoppingCart(user);
            model.addAttribute("shoppingCarts", shoppingCarts);
        }

        return "client/checkout";
    }

    @GetMapping("/shop/order")
    public String order(Principal principal,Model model){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = loginService.findByUsername(username);
        List<Order> orders = user.getOrders();
        model.addAttribute("orders", orders);
        return "client/order";
    }
    @PostMapping("/shop/save-order")
    public String saveOrder(Principal principal){
        if(principal == null){
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = loginService.findByUsername(username);
        
        orderService.saveOrder(user);
        shoppingCartService.clearShoppingCart(user);

        return "redirect:/shop/order";
    }

}
