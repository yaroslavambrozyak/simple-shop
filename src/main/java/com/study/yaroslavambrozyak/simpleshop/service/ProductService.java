package com.study.yaroslavambrozyak.simpleshop.service;

import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductService {

    Product getProduct(Long id);

    List<Product> getProductsByCategory(Long categoryId);

    List<Product> getFilteredProductsByCategory(Specification<Product> specification);

}
