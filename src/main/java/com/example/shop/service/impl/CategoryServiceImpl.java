package com.example.shop.service.impl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop.entity.Category;
import com.example.shop.exception.NotFoundException;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        Category newCategory = new Category(category.getName());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Optional<Category> getById(long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category;
    }
  

    @Override
    public Category update(Category category) {
        Category newCategory = categoryRepository.findById(category.getId()).get();
        newCategory.setName(category.getName());
        newCategory.set_activated(category.is_activated());
        newCategory.set_deleted(category.is_deleted());
        
        return categoryRepository.save(newCategory);
    }

    @Override
    public void deleteById(long id) {
        Optional<Category> rs = categoryRepository.findById(id);
        if(rs.isEmpty()){
            throw new NotFoundException("Not Found Category!");
        }
        Category category = rs.get();
        category.set_activated(false);
        category.set_deleted(true);
        categoryRepository.save(category);
    }

    @Override
    public void enableById(long id) {
        Optional<Category> rs = categoryRepository.findById(id);
        if(rs.isEmpty()){
            throw new NotFoundException("Not Found Category!");
        }
        Category category = rs.get();
        category.set_activated(true);
        category.set_deleted(false);
        categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllByActivated() {
        return categoryRepository.findAllByActivated();
    }



    
}
