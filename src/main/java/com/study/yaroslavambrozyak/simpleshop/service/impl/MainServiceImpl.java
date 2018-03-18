package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;
import com.study.yaroslavambrozyak.simpleshop.repository.RootCategoryRepository;
import com.study.yaroslavambrozyak.simpleshop.service.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImpl implements MainService {

    private Logger logger = LoggerFactory.getLogger(MainServiceImpl.class);

    @Autowired
    private RootCategoryRepository rootCategoryRepository;

    @Cacheable(value = "rootCategory")
    @Override
    public List<RootCategory> getRootCategories() {
        logger.debug("getRoot");
        return rootCategoryRepository.findAll();
    }
}
