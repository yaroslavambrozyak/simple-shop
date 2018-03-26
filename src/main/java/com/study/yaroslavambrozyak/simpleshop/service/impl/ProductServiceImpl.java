package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import com.study.yaroslavambrozyak.simpleshop.exception.NotEnoughException;
import com.study.yaroslavambrozyak.simpleshop.exception.NotFoundException;
import com.study.yaroslavambrozyak.simpleshop.repository.ProductRepository;
import com.study.yaroslavambrozyak.simpleshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
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
    public Product getProductForOrder(Long id, Integer quantity) throws Exception {
        Product productForOrder = productRepository.findProductById(id);
        if (productForOrder.getQuantity()<quantity){
            throw new NotEnoughException();
        }
        return productForOrder;
    }

    @Override
    public Page<Product> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findAllByCategoryId(categoryId,pageable);
    }

    @Override
    public Page<Product> getFilteredProductsByCategory(Specification<Product> specification, Pageable pageable) {
        return productRepository.findAll(specification,pageable);
    }
}
