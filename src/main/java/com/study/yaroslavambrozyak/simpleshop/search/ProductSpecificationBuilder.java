package com.study.yaroslavambrozyak.simpleshop.search;

import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecificationBuilder {

    private final List<SearchCriteria> params;

    public ProductSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public ProductSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Product> build() {
        if (params.size() == 0) {
            return null;
        }
        List<Specification<Product>> specifications = new ArrayList<>();
        params.forEach(param -> specifications.add(new ProductSpecification(param)));
        Specification<Product> result = specifications.get(0);
        for(int i = 1;i<specifications.size();i++){
            result = result.and(specifications.get(i));
        }
        return result;
    }
}
