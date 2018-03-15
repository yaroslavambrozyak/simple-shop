package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.entity.SubCategory;
import com.study.yaroslavambrozyak.simpleshop.repository.SubCategoryRepository;
import com.study.yaroslavambrozyak.simpleshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Override
    public List<SubCategory> getSubCategoriesById(Long id) {
        return subCategoryRepository.findAllById(id);
    }
}