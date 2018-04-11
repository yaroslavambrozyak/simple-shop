package com.study.yaroslavambrozyak.simpleshop.testutil;

import com.study.yaroslavambrozyak.simpleshop.entity.SubCategory;

public class CategoryBuilder {

    private SubCategory category;

    public CategoryBuilder() {
        category = new SubCategory();
    }

    public CategoryBuilder id(Long id) {
        category.setId(id);
        return this;
    }

    public CategoryBuilder name(String name) {
        category.setName(name);
        return this;
    }

    public CategoryBuilder imagePath(String imagePath){
        category.setImagePath(imagePath);
        return this;
    }

    public SubCategory build(){
        return category;
    }

}
