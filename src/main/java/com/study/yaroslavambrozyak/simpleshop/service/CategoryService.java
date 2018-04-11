package com.study.yaroslavambrozyak.simpleshop.service;

import com.study.yaroslavambrozyak.simpleshop.dto.SubCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.SubCategory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface CategoryService {

    List<SubCategory> getSubCategoriesById(Long id);

    List<SubCategory> getAllSubCategories();

    SubCategory getSubCategory(Long id);

    void addSubCategory(SubCategoryDTO subCategoryDTO);

    void updateSubCategory(SubCategoryDTO subCategoryDTO);

    void deleteSubCategory(Long id);

}
