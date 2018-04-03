package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.dto.RootCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;
import com.study.yaroslavambrozyak.simpleshop.exception.NotFoundException;
import com.study.yaroslavambrozyak.simpleshop.repository.RootCategoryRepository;
import com.study.yaroslavambrozyak.simpleshop.service.RootCategoryService;
import com.study.yaroslavambrozyak.simpleshop.util.ImageUtil;
import com.study.yaroslavambrozyak.simpleshop.util.NullAwareBeanUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RootCategoryServiceImpl implements RootCategoryService {

    private RootCategoryRepository rootCategoryRepository;
    private ModelMapper modelMapper;
    private NullAwareBeanUtil nullAwareBean;
    private ImageUtil imageUtil;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public RootCategoryServiceImpl(RootCategoryRepository rootCategoryRepository, ModelMapper modelMapper,
                                   NullAwareBeanUtil nullAwareBean, ImageUtil imageUtil) {
        this.rootCategoryRepository = rootCategoryRepository;
        this.modelMapper = modelMapper;
        this.nullAwareBean = nullAwareBean;
        this.imageUtil = imageUtil;
    }

    // TODO fix cache
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
    public void addRootCategory(RootCategoryDTO rootCategoryDTO, CommonsMultipartFile image) {
        try {
            String path = imageUtil.saveImage(image);
            RootCategory rootCategory = modelMapper.map(rootCategoryDTO, RootCategory.class);
            rootCategory.setImagePath(path);
            rootCategoryRepository.save(rootCategory);
        } catch (IOException e) {
            logger.error("IOExc",e);
        }
    }

    @CacheEvict(value = "rootCategory", allEntries = true)
    @Override
    public void updateRootCategory(RootCategoryDTO rootCategoryDTO) {
        RootCategory rootCategory = this.getRootCategory(rootCategoryDTO.getId());
        nullAwareBean.copyProperties(rootCategoryDTO, rootCategory);
        rootCategoryRepository.save(rootCategory);
    }

    @CacheEvict(value = "rootCategory",allEntries = true)
    @Override
    public void deleteRootCategory(Long id) {
        rootCategoryRepository.deleteById(id);
    }
}
