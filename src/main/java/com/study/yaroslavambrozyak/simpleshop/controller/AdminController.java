package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.dto.ProductDTO;
import com.study.yaroslavambrozyak.simpleshop.dto.RootCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.dto.SubCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.service.CategoryService;
import com.study.yaroslavambrozyak.simpleshop.service.ProductService;
import com.study.yaroslavambrozyak.simpleshop.service.RootCategoryService;
import com.study.yaroslavambrozyak.simpleshop.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RootCategoryService rootCategoryService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ImageUtil imageUtil;

    @Autowired
    public AdminController(RootCategoryService rootCategoryService, CategoryService categoryService,
                           ProductService productService,ImageUtil imageUtil) {
        this.rootCategoryService = rootCategoryService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.imageUtil = imageUtil;
    }

    @PostMapping("/root")
    public String addRootCategory(RootCategoryDTO rootCategoryDTO, CommonsMultipartFile image) throws IOException {
        String path = imageUtil.saveImage(image);
        rootCategoryService.addRootCategory(rootCategoryDTO,path);
        return null;
    }

    @PutMapping("/root")
    public String updateRootCategory(RootCategoryDTO rootCategoryDTO){
        rootCategoryService.updateRootCategory(rootCategoryDTO);
        return null;
    }

    @DeleteMapping("/root")
    public String deleteRootCategory(@RequestParam("id") Long id){
        rootCategoryService.deleteRootCategory(id);
        return null;
    }

    @PostMapping("/category")
    public String addSubCategory(SubCategoryDTO subCategoryDTO, CommonsMultipartFile image) throws IOException {
        String path = imageUtil.saveImage(image);
        categoryService.addSubCategory(subCategoryDTO,path);
        return null;
    }

    @PutMapping("/category")
    public String updateSubCategory(SubCategoryDTO subCategoryDTO){
        categoryService.updateSubCategory(subCategoryDTO);
        return null;
    }

    @PostMapping("/product")
    public String addProduct(ProductDTO productDTO,CommonsMultipartFile[] images) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        for(CommonsMultipartFile image : images){
            String path = imageUtil.saveImage(image);
            imagePaths.add(path);
        }
        productService.addProduct(productDTO,imagePaths);
        return null;
    }

    @ExceptionHandler(value = IOException.class)
    public String excHandler(){
        return null;
    }



}
