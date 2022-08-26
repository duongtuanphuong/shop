package com.example.shop.controller.Admin;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.shop.entity.Category;
import com.example.shop.entity.Product;
import com.example.shop.model.dto.ProductDto;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;    

    // @GetMapping("/product")
    // public String getProduct(Model model,Principal principal){
    //     if(principal == null){
    //         return "redirect:/login";
    //     }
    //     List<ProductDto> products = productService.findAll();
    //     model.addAttribute("products", products);
    //     model.addAttribute("size", products.size());

    //     return "product";
    // }
    
    @GetMapping("admin/product/add-product")
    public String addProduct(Model model,Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllByActivated(); 
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductDto());
        model.addAttribute("title", "Add New Product");
        return "admin/add-product";
    }

    @PostMapping("admin/product/save-product")
    public String saveProduct(@ModelAttribute("product") ProductDto productDto,@RequestParam("imageProduct") MultipartFile multipartFile,RedirectAttributes attributes ){

        try {
            productService.save(productDto, multipartFile);
            attributes.addFlashAttribute("success", "Add New Product Success!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed To Add New Product!");
        }

        return "redirect:/admin/product/0";
    }
    
    @GetMapping("admin/product/update-product/{id}")
    public String updateProductForm(@PathVariable("id")long id,Model model,Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        ProductDto productDto = productService.findProductById(id);
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        model.addAttribute("title", "Update Product");
        return "admin/update-product";
    }

    @PostMapping("admin/product/update-product/{id}")
    public String updateProduct(@PathVariable("id") long id,
                                @ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct")MultipartFile multipartFile,
                                RedirectAttributes attributes){
        try {
            productService.update(productDto, multipartFile);
            attributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update");
        }
        return "redirect:/admin/product/0";
    }


    @RequestMapping(value = "admin/product/enabled-product/{id}",method = {RequestMethod.PUT,RequestMethod.GET})
    public String enabledProduct(@PathVariable("id") long id,RedirectAttributes attributes){
        try{
            productService.enableById(id);
            attributes.addFlashAttribute("success", "Enabled successsfully!");
        }catch(Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Enabled failed!");
        }
        return "redirect:/admin/product/0";
    }

    @RequestMapping(value = "admin/product/deleted-product/{id}",method = {RequestMethod.PUT,RequestMethod.GET})
    public String deletedProduct(@PathVariable("id") long id,RedirectAttributes attributes){
        try{
            productService.deleteById(id);
            attributes.addFlashAttribute("success", "Deleted successsfully!");
        }catch(Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Deleted failed!");
        }
        return "redirect:/admin/product/0";
    }


    @GetMapping("admin/product/{pageNo}")
    public String productPage(@PathVariable("pageNo") int pageNo,Model model,Principal principal){

        if(principal == null){
            return "redirect:/login";
        }
        Page<ProductDto> productDtoList = productService.pageProduct(pageNo);
        model.addAttribute("size", productDtoList.getSize());
        model.addAttribute("totalPages",productDtoList.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("products", productDtoList);
        model.addAttribute("title", "Product");
        return "admin/product";
    }


    @GetMapping("/search-result/{pageNo}")
    public String searchProducts(@PathVariable("pageNo") int pageNo,@RequestParam("keyword") String keyword,Model model,Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        Page<ProductDto> products = productService.searchProducts(pageNo, keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("products", products);
        model.addAttribute("size", products.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());

        return "result-product";
    }


}
