package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.dto.UserDTO;
import com.study.yaroslavambrozyak.simpleshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error",required = false)String error,Model model){
        /*if (error!=null){
            model.addAttribute("err","err");
        }*/
        return "login";
    }

    //test
    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user",new UserDTO());
        return "registration";
    }

    @PostMapping("/registrationProcess")
    public String registrationProcess(@ModelAttribute("user") UserDTO userDTO){
        userService.register(userDTO);
        return "redirect:/login";
    }
}
