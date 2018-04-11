package com.study.yaroslavambrozyak.simpleshop.service;

import com.study.yaroslavambrozyak.simpleshop.dto.RootCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface RootCategoryService {

    List<RootCategory> getRootCategories();

    RootCategory getRootCategory(Long id);

    void addRootCategory(RootCategoryDTO rootCategory);

    void updateRootCategory(RootCategoryDTO rootCategoryDTO);

    void deleteRootCategory(Long id);

}
