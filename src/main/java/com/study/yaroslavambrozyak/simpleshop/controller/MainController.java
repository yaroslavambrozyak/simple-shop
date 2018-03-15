package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.SimpleShopApplication;
import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;
import com.study.yaroslavambrozyak.simpleshop.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("/")
    public String index(Model model){
        List<RootCategory> categories = mainService.getRootCategories();
        categories.forEach(rootCategory ->
                        rootCategory.setImagePath("/img/"+rootCategory.getImagePath()));
        model.addAttribute("categories",categories);
        return "index";
    }

}
