package com.example.shop.controller.Public;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shop.entity.Product;
import com.example.shop.service.ProductService;

@Controller
public class HomeController {
    
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String getHome(Model model,@RequestParam(required = false) Integer page){
        if (page == null) {
            page = 0;
        } else {
            page--;
            if (page < 0) {
                page = 0;
            }
        }
        Page<Product> products = productService.getProductInShop(page);
        List<Product> fillterHighPrices = productService.fillterHighPrice();
        Page<Product> topPriceProducts = productService.topPriceProduct(page);
        model.addAttribute("products", products);
        model.addAttribute("fillterHighPrices", fillterHighPrices);
        model.addAttribute("topPriceProducts", topPriceProducts);
        return "client/index";
    }

    @GetMapping("/shop/product/{id}")
    public String getProductById(@PathVariable("id") long id,Model model,@RequestParam(required = false) Integer page){
        if (page == null) {
            page = 0;
        } else {
            page--;
            if (page < 0) {
                page = 0;
            }
        }

        Product product = productService.getProductById(id);
        Page<Product> relatedProducts = productService.getProductByCategory(product.getCategory().getId(), page);

        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);
        return "client/product-detail";
    }
}
