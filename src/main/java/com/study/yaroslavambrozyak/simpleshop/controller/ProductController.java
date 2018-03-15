package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import com.study.yaroslavambrozyak.simpleshop.service.ProductService;
import com.study.yaroslavambrozyak.simpleshop.util.ProductSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String getProducts(@RequestParam("id") Long categoryId,
                              @RequestParam(value = "filter", required = false) String filter, Model model) {
        if (filter != null) {
            ProductSpecificationBuilder builder = new ProductSpecificationBuilder();
            Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
            Matcher matcher = pattern.matcher(filter + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
            Specification<Product> specification = builder.build();
            List<Product> products = productService.getFilteredProductsByCategory(categoryId, specification);
            model.addAttribute("prducts",products);
        } else {
            List<Product> products = productService.getProductsByCategory(categoryId);
            model.addAttribute("products", products);
        }
        return "products";
    }

    @GetMapping("/product")
    public String getProduct(@RequestParam("id") Long id, Model model) {
        return null;
    }
}
