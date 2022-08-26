package com.example.shop.controller.Public;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shop.entity.ShoppingCart;
import com.example.shop.entity.User;
import com.example.shop.service.LoginService;
import com.example.shop.service.ProductService;
import com.example.shop.service.ShoppingCartService;

@Controller
public class CartController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartService shoppingCartService;


    @GetMapping("/shop/cart")
    public String cart(Model model,Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = loginService.findByUsername(username);
        List<ShoppingCart> shoppingCart = shoppingCartService.listShoppingCart(user);
        model.addAttribute("totalItem", shoppingCart.size());
        model.addAttribute("shoppingCarts", shoppingCart);
        return "/client/cart";
    }

    @PostMapping("/shop/add-to-cart/{pid}")
    public String addToCart(@PathVariable("pid") long productId,Principal principal,HttpServletRequest request){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = loginService.findByUsername(username);
        ShoppingCart shoppingCart = shoppingCartService.addItemToCart(productId, user);
        return "redirect:" + request.getHeader("Referer");

    }

    @PostMapping("/shop/delete-item/{id}")
    public String deleteItem(@PathVariable("id") long productId,Principal principal,HttpServletRequest request){

        String username = principal.getName();
        User user = loginService.findByUsername(username);
        shoppingCartService.deleteItemInCart(productId, user);
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/shop/update-item/{id}")
    public String updateItem(@PathVariable("id") long productId,@RequestParam("qty") int quantity,Principal principal,HttpServletRequest request){
        String username = principal.getName();
        User user = loginService.findByUsername(username);
        shoppingCartService.updateItemInCart(productId, quantity, user);
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/shop/clear-cart")
    public String clearCart(Principal principal,HttpServletRequest request){
        String username = principal.getName();
        User user = loginService.findByUsername(username);
        shoppingCartService.clearShoppingCart(user);
        return "redirect:" + request.getHeader("Referer");
    }
}
