package com.study.yaroslavambrozyak.simpleshop.testutil;

import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;

public class RootCategoryBuilder {

    private RootCategory rootCategory;

    public RootCategoryBuilder() {
        rootCategory = new RootCategory();
    }

    public RootCategoryBuilder id(Long id){
        rootCategory.setId(id);
        return this;
    }

    public RootCategoryBuilder name(String name){
        rootCategory.setName(name);
        return this;
    }

    public RootCategoryBuilder imagePath(String imagePath){
        rootCategory.setImagePath(imagePath);
        return this;
    }

    public RootCategory build(){
        return this.rootCategory;
    }
}
