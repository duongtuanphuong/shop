package com.example.shop.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.shop.entity.Category;
import com.example.shop.entity.Product;
import com.example.shop.model.dto.ProductDto;

public interface ProductService {
    List<ProductDto > findAll();

    Product save(ProductDto productDto,MultipartFile multipartFile);

    Product update(ProductDto productDto,MultipartFile multipartFile);

    void deleteById(long id);

    void enableById(long id);

    ProductDto findProductById(long id);

    Page<ProductDto> pageProduct(int pageNo);

    Page<ProductDto> searchProducts(int pageNo,String keyword);

    List<Product> getAllProducts();

    Product getProductById(long id);

    Page<Product> getProductByCategory(long categoryId,int page);

    List<Product> fillterHighPrice();

    Page<Product> topPriceProduct(int page);

    Page<Product> getProductInShop(int page);
}
