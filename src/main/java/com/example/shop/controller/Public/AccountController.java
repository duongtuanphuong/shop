package com.example.shop.controller.Public;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.shop.entity.User;
import com.example.shop.service.LoginService;

@Controller
public class AccountController {
    
    @Autowired
    private LoginService loginService;

    @GetMapping("/shop/account")
    public String accountHome(Model model,Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = loginService.findByUsername(username);
        model.addAttribute("user", user);
        return "client/account";
    }

    @RequestMapping(value = "/shop/update-user",method = {RequestMethod.GET,RequestMethod.PUT})
    public String updateUser(@ModelAttribute("user") User user,Model model,RedirectAttributes redirectAttributes,Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        User updateUser = loginService.updateUser(user);
        redirectAttributes.addFlashAttribute("user", updateUser);
        return "redirect:/shop/account";
    }
}
