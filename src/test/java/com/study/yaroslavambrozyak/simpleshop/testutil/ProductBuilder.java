package com.study.yaroslavambrozyak.simpleshop.testutil;

import com.study.yaroslavambrozyak.simpleshop.entity.Product;

public class ProductBuilder {

    private Product product;

    public ProductBuilder(){
        product = new Product();
    }

    public ProductBuilder id(Long id){
        product.setId(id);
        return this;
    }

    public ProductBuilder name(String name){
        product.setName(name);
        return this;
    }

    public ProductBuilder description(String description){
        product.setDescription(description);
        return this;
    }

    public ProductBuilder quantity(int quantity){
        product.setQuantity(quantity);
        return this;
    }

    public Product build(){
        return product;
    }
}
