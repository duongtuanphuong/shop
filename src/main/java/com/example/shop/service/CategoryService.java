package com.example.shop.service;

import java.util.List;
import java.util.Optional;

import com.example.shop.entity.Category;

public interface CategoryService {
    List<Category> findAll();

    Category save(Category category);

    Optional<Category> getById(long id);

    Category update(Category category);


    void deleteById(long id);

    void enableById(long id);

    List<Category> findAllByActivated();
}
