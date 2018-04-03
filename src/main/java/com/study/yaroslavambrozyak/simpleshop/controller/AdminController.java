package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.dto.ProductDTO;
import com.study.yaroslavambrozyak.simpleshop.dto.RootCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.dto.SubCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.service.CategoryService;
import com.study.yaroslavambrozyak.simpleshop.service.ProductService;
import com.study.yaroslavambrozyak.simpleshop.service.RootCategoryService;
import com.study.yaroslavambrozyak.simpleshop.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private RootCategoryService rootCategoryService;
    private CategoryService categoryService;
    private ProductService productService;
    private ImageUtil imageUtil;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public AdminController(RootCategoryService rootCategoryService, CategoryService categoryService,
                           ProductService productService, ImageUtil imageUtil) {
        this.rootCategoryService = rootCategoryService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.imageUtil = imageUtil;
    }

    @PostMapping("/root")
    public String addRootCategory(RootCategoryDTO rootCategoryDTO, CommonsMultipartFile image) throws IOException {
        rootCategoryService.addRootCategory(rootCategoryDTO, image);
        return null;
    }

    @PutMapping("/root")
    public String updateRootCategory(RootCategoryDTO rootCategoryDTO) {
        rootCategoryService.updateRootCategory(rootCategoryDTO);
        return null;
    }

    @DeleteMapping("/root")
    public String deleteRootCategory(@RequestParam("id") Long id) {
        rootCategoryService.deleteRootCategory(id);
        return null;
    }

    @PostMapping("/category")
    public String addSubCategory(SubCategoryDTO subCategoryDTO, CommonsMultipartFile image) throws IOException {
        categoryService.addSubCategory(subCategoryDTO, image);
        return null;
    }

    @PutMapping("/category")
    public String updateSubCategory(SubCategoryDTO subCategoryDTO) {
        categoryService.updateSubCategory(subCategoryDTO);
        return null;
    }

    @PostMapping("/product")
    public Callable<String> addProduct(ProductDTO productDTO, CommonsMultipartFile[] images) throws IOException {
        return () -> {
            productService.addProduct(productDTO,images);
            return null;
        };
    }

    @DeleteMapping("/product")
    public String deleteProduct(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return null;
    }
}
