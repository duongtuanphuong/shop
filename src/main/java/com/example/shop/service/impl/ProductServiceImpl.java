package com.example.shop.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.shop.entity.Category;
import com.example.shop.entity.Product;
import com.example.shop.model.dto.ProductDto;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.ProductService;
import com.example.shop.utils.ImageUpload;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();

        List<ProductDto> productDtoList = transfer(products);
        return productDtoList;
    }

    @Override
    public Product save(ProductDto productDto,MultipartFile multipartFile)  {
        try {

            Product product = new Product();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCategory(productDto.getCategory());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());

            if(multipartFile == null){
                product.setImage(null);
            }
            else{
                imageUpload.uploadImage(multipartFile);
                product.setImage(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
            }
            product.set_activated(true);
            product.set_deleted(false);

            return productRepository.save(product);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public Product update(ProductDto productDto,MultipartFile multipartFile) {
        try {
            Product product = productRepository.findById(productDto.getId()).get();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setSalePrice(productDto.getSalePrice());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCategory(productDto.getCategory());
            if(multipartFile == null){
                product.setImage(product.getImage());
            }
            else{
                if(imageUpload.checkExisted(multipartFile) == false){
                    imageUpload.uploadImage(multipartFile);
                }
                product.setImage(Base64.getEncoder().encodeToString(multipartFile.getBytes()));

            }
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(long id) {
        Product product = productRepository.findById(id).get();
        product.set_deleted(true);
        product.set_activated(false);
        productRepository.save(product);
    }

    @Override
    public void enableById(long id) {
        Product product = productRepository.findById(id).get();
        product.set_deleted(false);
        product.set_activated(true);
        productRepository.save(product);
    }

    @Override
    public ProductDto findProductById(long id) {
        Product product = productRepository.findById(id).get();
        ProductDto productDto  =  new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setImage(product.getImage());
        productDto.setDeleted(product.is_deleted());
        productDto.setActivated(product.is_activated());
        productDto.setCategory(product.getCategory() );
        return productDto;
    }

   

    @Override
    public Page<ProductDto> searchProducts(int pageNo,String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDto> productDtoList = transfer(productRepository.searchProduct(keyword));
        Page<ProductDto> products = toPage(productDtoList, pageable);
        return products;
    }

   

    private Page toPage(List<ProductDto> list,Pageable pageable){
        if(pageable.getOffset() >= list.size()){
            return Page.empty();
        }

        long startIndex = pageable.getOffset();
        long endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size()) ? list.size() : pageable.getOffset() + pageable.getPageSize();
        List sublist = list.subList((int)startIndex, (int)endIndex);

        return new PageImpl<>(sublist,pageable,list.size()); 
    }

    private List<ProductDto> transfer(List<Product> products){
        List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product : products){
            ProductDto productDto  = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setImage(product.getImage());
            productDto.setDeleted(product.is_deleted());
            productDto.setActivated(product.is_activated());
            productDto.setCategory(product.getCategory() );
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    public List<Product> getAllProducts() {
        // TODO Auto-generated method stub
        return productRepository.getAllProducts();
    }

    @Override
    public Product getProductById(long id) {
        // TODO Auto-generated method stub
        return productRepository.findById(id).get();
    }

    @Override
    public Page<Product> getProductByCategory(long categoryId,int page) {
        // TODO Auto-generated method stub
        Page<Product> relatedProduct = productRepository.getProductByCategory(categoryId,PageRequest.of(page,10,Sort.by("id").descending()));
        return relatedProduct;
    }

    @Override
    public List<Product> fillterHighPrice() {
        // TODO Auto-generated method stub
        return productRepository.fillterHighPrice();
    }

    @Override
    public Page<Product> topPriceProduct(int page) {
        // TODO Auto-generated method stub
        Page<Product> products = productRepository.pageProduct(PageRequest.of(page,1,Sort.by("costPrice").descending()));
        return products;
    }



    @Override
    public Page<Product> getProductInShop(int page) {
        // TODO Auto-generated method stub
        Page<Product> products = productRepository.pageProduct(PageRequest.of(page, 8, Sort.by("id").descending()));
        return products;
    }

    @Override
    public Page<ProductDto> pageProduct(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDto> products = transfer(productRepository.findAll());
        Page<ProductDto> productPages = toPage(products, pageable);
        return productPages;
    }

}
