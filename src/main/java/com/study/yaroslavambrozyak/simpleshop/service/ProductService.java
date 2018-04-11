package com.study.yaroslavambrozyak.simpleshop.service;

import com.study.yaroslavambrozyak.simpleshop.dto.ProductDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import com.study.yaroslavambrozyak.simpleshop.exception.NotEnoughException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface ProductService {

    Product getProduct(Long id);

    Product getProductForOrder(Long id, Integer quantity) throws NotEnoughException;

    Page<Product> getProductsByCategory(Long categoryId, Pageable pageable);

    Page<Product> getFilteredProductsByCategory(Specification<Product> specification, Pageable pageable);

    void subtractQuantity(Product product,int quantity);

    void addProduct(ProductDTO productDTO);

    void deleteProduct(Long id);

}
