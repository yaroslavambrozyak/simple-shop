package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.dto.RootCategoryDTO;
import com.study.yaroslavambrozyak.simpleshop.service.RootCategoryService;
import com.study.yaroslavambrozyak.simpleshop.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RootCategoryService rootCategoryService;
    @Autowired
    private ImageUtil imageUtil;

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

    @ExceptionHandler(value = IOException.class)
    public String excHandler(){
        return null;
    }



}
