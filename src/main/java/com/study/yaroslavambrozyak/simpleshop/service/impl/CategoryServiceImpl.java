package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.dto.SubCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;
import com.study.yaroslavambrozyak.simpleshop.entity.SubCategory;
import com.study.yaroslavambrozyak.simpleshop.exception.NotFoundException;
import com.study.yaroslavambrozyak.simpleshop.repository.SubCategoryRepository;
import com.study.yaroslavambrozyak.simpleshop.service.CategoryService;
import com.study.yaroslavambrozyak.simpleshop.service.RootCategoryService;
import com.study.yaroslavambrozyak.simpleshop.util.NullAwareBeanUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final RootCategoryService rootCategoryService;
    private final ModelMapper modelMapper;
    private final NullAwareBeanUtil nullAwareBean;

    @Autowired
    public CategoryServiceImpl(SubCategoryRepository subCategoryRepository, RootCategoryService rootCategoryService
            , ModelMapper modelMapper, NullAwareBeanUtil nullAwareBean) {
        this.subCategoryRepository = subCategoryRepository;
        this.rootCategoryService = rootCategoryService;
        this.modelMapper = modelMapper;
        this.nullAwareBean = nullAwareBean;
    }

    @Transactional(readOnly = true)
    @Override
    @Cacheable(value = "subCategories")
    public List<SubCategory> getSubCategoriesById(Long id) {
        return subCategoryRepository.findAllByRootCategory_Id(id);
    }

    @Override
    public SubCategory getSubCategory(Long id) {
        return subCategoryRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @CacheEvict(value = "subCategories",allEntries = true)
    @Override
    public void addSubCategory(SubCategoryDTO subCategoryDTO,String imagePath){
        SubCategory subCategory = modelMapper.map(subCategoryDTO,SubCategory.class);
        subCategory.setImagePath(imagePath);
        subCategory.setRootCategory(rootCategoryService.getRootCategory(subCategoryDTO.getRootCategoryId()));
        subCategoryRepository.save(subCategory);
    }

    @CacheEvict(value = "subCategories",allEntries = true)
    @Override
    public void updateSubCategory(SubCategoryDTO subCategoryDTO) {
        SubCategory subCategory = getSubCategory(subCategoryDTO.getId());
        if (subCategory.getRootCategory().getId()!=subCategoryDTO.getId()){
            RootCategory rootCategory = rootCategoryService.getRootCategory(subCategoryDTO.getId());
            subCategory.setRootCategory(rootCategory);
        }
        nullAwareBean.copyProperties(subCategoryDTO,subCategory);
        subCategoryRepository.save(subCategory);
    }
}
