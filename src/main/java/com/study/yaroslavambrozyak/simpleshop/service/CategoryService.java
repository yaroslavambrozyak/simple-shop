package com.study.yaroslavambrozyak.simpleshop.service;

import com.study.yaroslavambrozyak.simpleshop.entity.SubCategory;

import java.util.List;

public interface CategoryService {

    List<SubCategory> getSubCategoriesById(Long id);

}
