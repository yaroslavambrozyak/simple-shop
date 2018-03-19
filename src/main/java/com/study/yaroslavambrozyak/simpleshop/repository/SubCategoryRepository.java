package com.study.yaroslavambrozyak.simpleshop.repository;

import com.study.yaroslavambrozyak.simpleshop.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {

    List<SubCategory> findAllByRootCategory_Id(Long id);
}
