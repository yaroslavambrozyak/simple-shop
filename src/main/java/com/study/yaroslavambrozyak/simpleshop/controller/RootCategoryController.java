package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;
import com.study.yaroslavambrozyak.simpleshop.service.RootCategoryService;
import com.study.yaroslavambrozyak.simpleshop.util.AjaxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class RootCategoryController {

    private RootCategoryService mainService;

    @Autowired
    public RootCategoryController(RootCategoryService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){
        List<RootCategory> categories = mainService.getRootCategories();
        model.addAttribute("categories",categories);
        return AjaxUtil.isAjax(request)?"/fragment/root-category-fragment":"index";
    }

}
