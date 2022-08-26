package com.example.shop.controller.Admin;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.shop.entity.Category;
import com.example.shop.service.CategoryService;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/category")
    public String getCategoryPage(Model model,Principal principal){
        
        if(principal == null){
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("categoryNew", new Category());
        model.addAttribute("title", "Category");
        return "admin/category";
    }

    @PostMapping("admin/category/add-category")
    public String add(@ModelAttribute("categoryNew") Category category,RedirectAttributes attributes){
        try{
            categoryService.save(category);
            attributes.addFlashAttribute("success", "Add Successfully");
        }catch(Exception e){
            attributes.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/category";
    }



    @RequestMapping(value = "admin/category/getById", method= {RequestMethod.PUT,RequestMethod.GET})
    @ResponseBody
    public Optional<Category> getCategoryById(long id){
        return categoryService.getById(id);
    }

    @GetMapping("/admin/category/update-category")
    public String update( Category category,RedirectAttributes attributes){
        try{
            categoryService.update(category);
            attributes.addFlashAttribute("success", "Update Successfully");
        }catch(Exception e){
            attributes.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/category";
    }

    @RequestMapping(value = "admin/category/delete-category", method = {RequestMethod.PUT,RequestMethod.GET})
    public String delete(long id,RedirectAttributes attributes){
        try{
            categoryService.deleteById(id);
            attributes.addFlashAttribute("success", "Delete Success");
        }catch(Exception e){
            e.printStackTrace();;
            attributes.addFlashAttribute("failed", "Delete Failed");
        }
        return "redirect:/admin/category";
    }
    
    @RequestMapping(value = "admin/category/enable-category", method = {RequestMethod.PUT,RequestMethod.GET})
    public String enable(long id,RedirectAttributes attributes){
        try{
            categoryService.enableById(id);
            attributes.addFlashAttribute("success", "Enable Success");
        }catch(Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Enable Failed");
        }
        return "redirect:/admin/category";
    }

   
}
