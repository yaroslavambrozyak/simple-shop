package com.study.yaroslavambrozyak.simpleshop.service;

import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;

import java.util.List;

public interface MainService {

    List<RootCategory> getRootCategories();

    void addRootCategory(RootCategory rootCategory);

}
