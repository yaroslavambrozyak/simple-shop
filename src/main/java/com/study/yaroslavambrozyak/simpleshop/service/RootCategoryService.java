package com.study.yaroslavambrozyak.simpleshop.service;

import com.study.yaroslavambrozyak.simpleshop.dto.RootCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;

import java.util.List;

public interface RootCategoryService {

    List<RootCategory> getRootCategories();

    void addRootCategory(RootCategoryDTO rootCategory, String imagePath);

    void updateRootCategory(RootCategoryDTO rootCategoryDTO);

    void deleteRootCategory(Long id);

}
