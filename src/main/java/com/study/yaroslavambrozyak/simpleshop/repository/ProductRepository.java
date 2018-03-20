package com.study.yaroslavambrozyak.simpleshop.repository;

import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long>,JpaSpecificationExecutor<Product> {

    Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);

}
