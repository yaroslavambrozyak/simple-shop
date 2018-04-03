package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import com.study.yaroslavambrozyak.simpleshop.service.ProductService;
import com.study.yaroslavambrozyak.simpleshop.util.AjaxUtil;
import com.study.yaroslavambrozyak.simpleshop.search.ProductSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class ProductController {

    private ProductService productService;
    private final int DEFAULT_SIZE = 10;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Pageable throws error ??????????
    @GetMapping("/products")
    public String getProducts(@RequestParam(value = "id") Long categoryId,
                              @RequestParam(value = "filter", required = false) String filter,
                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              HttpServletRequest request, Model model) {
        if (filter != null && !filter.isEmpty()) {
            Specification<Product> specification = createSpecification(categoryId, filter);
            Page<Product> products = productService
                    .getFilteredProductsByCategory(specification, PageRequest.of(page, DEFAULT_SIZE));
            model.addAttribute("products", products.getContent());
        } else {
            Page<Product> products = productService
                    .getProductsByCategory(categoryId, PageRequest.of(page, DEFAULT_SIZE));
            model.addAttribute("products", products.getContent());
        }
        return AjaxUtil.isAjax(request) ? "/fragment/products-fragment" : "products";
    }

    @GetMapping("/product")
    public String getProduct(@RequestParam("id") Long id, Model model) {
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "product";
    }

    private Specification<Product> createSpecification(Long id, String filter) {
        String categoryId = "category";
        String operationEqual = ":";
        ProductSpecificationBuilder builder = new ProductSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
        Matcher matcher = pattern.matcher(filter + ",");
        builder.with(categoryId, operationEqual, id);
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        return builder.build();
    }
}
