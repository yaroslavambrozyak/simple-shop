package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;
import com.study.yaroslavambrozyak.simpleshop.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MainService mainService;

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        RootCategory rootCategory = new RootCategory();
        rootCategory.setName("cached");
        rootCategory.setImagePath("qweqweqweqwe");
        mainService.addRootCategory(rootCategory);
        return "admin page";
    }


}
