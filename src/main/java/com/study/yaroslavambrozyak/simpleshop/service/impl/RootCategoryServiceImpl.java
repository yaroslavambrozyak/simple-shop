package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.dto.RootCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;
import com.study.yaroslavambrozyak.simpleshop.exception.NotFoundException;
import com.study.yaroslavambrozyak.simpleshop.repository.RootCategoryRepository;
import com.study.yaroslavambrozyak.simpleshop.service.RootCategoryService;
import com.study.yaroslavambrozyak.simpleshop.util.NullAwareBeanUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RootCategoryServiceImpl implements RootCategoryService {

    private final RootCategoryRepository rootCategoryRepository;
    private final ModelMapper modelMapper;
    private final NullAwareBeanUtil nullAwareBean;

    @Autowired
    public RootCategoryServiceImpl(RootCategoryRepository rootCategoryRepository, ModelMapper modelMapper,
                                   NullAwareBeanUtil nullAwareBean) {
        this.rootCategoryRepository = rootCategoryRepository;
        this.modelMapper = modelMapper;
        this.nullAwareBean = nullAwareBean;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "rootCategory")
    @Override
    public List<RootCategory> getRootCategories() {
        return rootCategoryRepository.findAll();
    }

    @Override
    public RootCategory getRootCategory(Long id) {
        return rootCategoryRepository.findById(id).orElseThrow(NotFoundException::new);
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
        RootCategory rootCategory = getRootCategory(rootCategoryDTO.getId());
        nullAwareBean.copyProperties(rootCategoryDTO, rootCategory);
        rootCategoryRepository.save(rootCategory);
    }

    @Override
    public void deleteRootCategory(Long id) {
        rootCategoryRepository.deleteById(id);
    }
}
