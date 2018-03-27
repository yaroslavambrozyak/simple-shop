package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.dto.RootCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;
import com.study.yaroslavambrozyak.simpleshop.repository.RootCategoryRepository;
import com.study.yaroslavambrozyak.simpleshop.service.RootCategoryService;
import com.study.yaroslavambrozyak.simpleshop.util.NullAwareBeanUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RootCategoryServiceImpl implements RootCategoryService {

    @Autowired
    private RootCategoryRepository rootCategoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private NullAwareBeanUtil nullAwareBean;

    @Cacheable(value = "rootCategory")
    @Override
    public List<RootCategory> getRootCategories() {
        return rootCategoryRepository.findAll();
    }

    @CacheEvict(value = "rootCategory", allEntries = true)
    @Override
    public void addRootCategory(RootCategoryDTO rootCategoryDTO, String imagePath) {
        RootCategory rootCategory = modelMapper.map(rootCategoryDTO, RootCategory.class);
        rootCategory.setImagePath(imagePath);
        rootCategoryRepository.save(rootCategory);
    }

    @CacheEvict(value = "rootCategory", allEntries = true)
    @Override
    public void updateRootCategory(RootCategoryDTO rootCategoryDTO) {
        rootCategoryRepository.findById(rootCategoryDTO.getId()).ifPresent(rootCategory -> {
                    nullAwareBean.copyProperties(rootCategoryDTO, rootCategory);
                    rootCategoryRepository.save(rootCategory);
                });
    }


    @Override
    public void deleteRootCategory(Long id) {
        rootCategoryRepository.deleteById(id);
    }
}
