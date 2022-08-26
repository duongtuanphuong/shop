package com.example.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.shop.entity.Category;
import com.example.shop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select p from Product p")
    Page<Product> pageProduct(Pageable pageable);


    @Query("select p from Product p where p.description like %?1% or p.name like %?1%")
    List<Product> searchProduct(String keyword);

    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false")
    List<Product> getAllProducts();

    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false and p.category.id = ?1")
    Page<Product> getProductByCategory(long categoryId,Pageable pageable);

    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false " + "order by p.costPrice desc")
    List<Product> fillterHighPrice();

}
