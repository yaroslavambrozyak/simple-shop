package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import com.study.yaroslavambrozyak.simpleshop.exception.NotFoundException;
import com.study.yaroslavambrozyak.simpleshop.repository.ProductRepository;
import com.study.yaroslavambrozyak.simpleshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.getAllByCategory_Id(categoryId);
        return products;
    }

    @Override
    public List<Product> getFilteredProductsByCategory(Long categoryId, Specification<Product> specification) {
        return productRepository.findAll(specification);
    }
}
