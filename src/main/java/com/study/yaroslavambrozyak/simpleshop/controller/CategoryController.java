package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.entity.SubCategory;
import com.study.yaroslavambrozyak.simpleshop.service.CategoryService;
import com.study.yaroslavambrozyak.simpleshop.util.AjaxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CategoryController {

    private CategoryService categoryService;

    private final String SUB_CATEGORY_FRAGMENT = "/fragment/sub-category-fragment";
    private final String SUB_CATEGORY_VIEW = "categories";

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public String getSubCategories(@RequestParam("id") Long id, HttpServletRequest request, Model model) {
        List<SubCategory> subCategories = categoryService.getSubCategoriesById(id);
        model.addAttribute("categories", subCategories);
        return AjaxUtil.isAjax(request) ? SUB_CATEGORY_FRAGMENT : SUB_CATEGORY_VIEW;
    }

}
