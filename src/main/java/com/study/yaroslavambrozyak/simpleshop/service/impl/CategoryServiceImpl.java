package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.dto.SubCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;
import com.study.yaroslavambrozyak.simpleshop.entity.SubCategory;
import com.study.yaroslavambrozyak.simpleshop.exception.NotFoundException;
import com.study.yaroslavambrozyak.simpleshop.repository.SubCategoryRepository;
import com.study.yaroslavambrozyak.simpleshop.service.CategoryService;
import com.study.yaroslavambrozyak.simpleshop.service.RootCategoryService;
import com.study.yaroslavambrozyak.simpleshop.util.ImageUtil;
import com.study.yaroslavambrozyak.simpleshop.util.NullAwareBeanUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private SubCategoryRepository subCategoryRepository;
    private RootCategoryService rootCategoryService;
    private ModelMapper modelMapper;
    private NullAwareBeanUtil nullAwareBean;
    private ImageUtil imageUtil;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public CategoryServiceImpl(SubCategoryRepository subCategoryRepository, RootCategoryService rootCategoryService
            , ModelMapper modelMapper, NullAwareBeanUtil nullAwareBean, ImageUtil imageUtil) {
        this.subCategoryRepository = subCategoryRepository;
        this.rootCategoryService = rootCategoryService;
        this.modelMapper = modelMapper;
        this.nullAwareBean = nullAwareBean;
        this.imageUtil = imageUtil;
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

    @CacheEvict(value = "subCategories", allEntries = true)
    @Override
    public void addSubCategory(SubCategoryDTO subCategoryDTO, CommonsMultipartFile image) {
        try {
            String path = imageUtil.saveImage(image);
            SubCategory subCategory = modelMapper.map(subCategoryDTO, SubCategory.class);
            subCategory.setImagePath(path);
            subCategory.setRootCategory(rootCategoryService.getRootCategory(subCategoryDTO.getRootCategoryId()));
            subCategoryRepository.save(subCategory);
        } catch (IOException e) {
            logger.error("IOExc",e);
        }
    }

    @CacheEvict(value = "subCategories", allEntries = true)
    @Override
    public void updateSubCategory(SubCategoryDTO subCategoryDTO) {
        SubCategory subCategory = getSubCategory(subCategoryDTO.getId());
        if (subCategory.getRootCategory().getId() != subCategoryDTO.getId()) {
            RootCategory rootCategory = rootCategoryService.getRootCategory(subCategoryDTO.getId());
            subCategory.setRootCategory(rootCategory);
        }
        nullAwareBean.copyProperties(subCategoryDTO, subCategory);
        subCategoryRepository.save(subCategory);
    }
}
