package com.study.yaroslavambrozyak.simpleshop.service;

import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductService {

    Product getProduct(Long id);

    Product getProductForOrder(Long id, Integer quantity) throws Exception;

    Page<Product> getProductsByCategory(Long categoryId, Pageable pageable);

    Page<Product> getFilteredProductsByCategory(Specification<Product> specification, Pageable pageable);

}
