package com.example.shop.controller.Admin;

import javax.validation.Valid;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.shop.entity.Role;
import com.example.shop.entity.User;
import com.example.shop.model.dto.UserDto;
import com.example.shop.security.AdminServiceConfig;
import com.example.shop.service.LoginService;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

@Controller
public class LoginController {
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @GetMapping("/admin/index")
    public String getAdminPage(Model model,HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        User user = loginService.findByUsername(authentication.getName());
        session.setAttribute("user", user);
        model.addAttribute("title","Admin Page");
        return "admin/index";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("adminDto", new UserDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model){
        return "forgot-password";
    }



    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") UserDto adminDto,BindingResult result,Model model){
    

        try{
            if(result.hasErrors()){
                model.addAttribute("adminDto", adminDto);
                result.toString();
                return "register";
            }
            String username = adminDto.getUsername();
            User admin = loginService.findByUsername(username);
            if(admin != null){
                model.addAttribute("emailError", "Your email has been registered!");
                System.out.println("admin not null");
                return "register";
            }
            if(adminDto.getPassword().equals(adminDto.getRepeatPassword())){
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                loginService.save(adminDto);
                model.addAttribute("adminDto", adminDto);
                System.out.print("success");
                model.addAttribute("success", "Register successfully! ");
            } else{
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Password is not same");
                System.out.println("password not same");
                return "register";
            }

        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("errors", "Can not register because error server!");
        }

        return "register";
    }
}
