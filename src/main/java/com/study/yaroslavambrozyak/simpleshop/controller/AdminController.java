package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.dto.ProductDTO;
import com.study.yaroslavambrozyak.simpleshop.dto.RootCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.dto.SubCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;
import com.study.yaroslavambrozyak.simpleshop.entity.SubCategory;
import com.study.yaroslavambrozyak.simpleshop.service.CategoryService;
import com.study.yaroslavambrozyak.simpleshop.service.ProductService;
import com.study.yaroslavambrozyak.simpleshop.service.RootCategoryService;
import com.study.yaroslavambrozyak.simpleshop.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    private final String ADMIN_VIEW = "admin";
    private final String ROOT_ADMIN_FRAGMENT = "/fragment/root-category-admin-fragment";
    private final String ROOT_ADMIN_FORM = "/fragment/add-root-category-form";
    private final String SUB_ADMIN_FRAGMENT = "/fragment/sub-category-admin-fragment";
    private final String SUB_ADMIN_FORM = "/fragment/sub-category-form";

    private final String REDIRECT_ADMIN = "redirect:/admin/panel";

    @Autowired
    public AdminController(RootCategoryService rootCategoryService, CategoryService categoryService,
                           ProductService productService) {
        this.rootCategoryService = rootCategoryService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/panel")
    public String panel() {
        return ADMIN_VIEW;
    }

    @GetMapping("/root")
    public String getRootCategories(Model model) {
        List<RootCategory> rootCategories = rootCategoryService.getRootCategories();
        model.addAttribute("categories", rootCategories);
        return ROOT_ADMIN_FRAGMENT;
    }

    @GetMapping("/rootCategoryForm")
    public String getRootForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null)
            model.addAttribute("category", rootCategoryService.getRootCategory(id));
        return ROOT_ADMIN_FORM;
    }

    @PostMapping("/root")
    public String addRootCategory(RootCategoryDTO rootCategoryDTO) throws IOException {
        rootCategoryService.addRootCategory(rootCategoryDTO);
        return REDIRECT_ADMIN;
    }

    @PutMapping("/root")
    public String updateRootCategory(RootCategoryDTO rootCategoryDTO) {
        rootCategoryService.updateRootCategory(rootCategoryDTO);
        return REDIRECT_ADMIN;
    }

    @DeleteMapping("/root")
    public String deleteRootCategory(@RequestParam("id") Long id) {
        rootCategoryService.deleteRootCategory(id);
        return REDIRECT_ADMIN;
    }

    @GetMapping("/category")
    public String getSubCategories(@RequestParam(value = "id", required = false) Long id, Model model) {
        List<SubCategory> subCategories;
        if (id != null)
            subCategories = categoryService.getSubCategoriesById(id);
        else
            subCategories = categoryService.getAllSubCategories();
        model.addAttribute("categories", subCategories);
        return SUB_ADMIN_FRAGMENT;
    }

    @GetMapping("/categoryForm")
    public String getCategoryForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null)
            model.addAttribute("category", categoryService.getSubCategory(id));
        return SUB_ADMIN_FORM;
    }

    @PostMapping("/category")
    public String addSubCategory(SubCategoryDTO subCategoryDTO) throws IOException {
        categoryService.addSubCategory(subCategoryDTO);
        return REDIRECT_ADMIN;
    }

    @PutMapping("/category")
    public String updateSubCategory(SubCategoryDTO subCategoryDTO) {
        categoryService.updateSubCategory(subCategoryDTO);
        return REDIRECT_ADMIN;
    }

    @DeleteMapping("/category")
    public String deleteSubCategory(@RequestParam("id") Long id) {
        categoryService.deleteSubCategory(id);
        return REDIRECT_ADMIN;
    }

    // release servlet thread idk????
    @PostMapping("/product")
    public Callable<String> addProduct(ProductDTO productDTO) throws IOException {
        return () -> {
            productService.addProduct(productDTO);
            return REDIRECT_ADMIN;
        };
    }

    @DeleteMapping("/product")
    public String deleteProduct(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return REDIRECT_ADMIN;
    }
}
