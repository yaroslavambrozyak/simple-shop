package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.dto.RegistrationUserDTO;
import com.study.yaroslavambrozyak.simpleshop.dto.UserDTO;
import com.study.yaroslavambrozyak.simpleshop.service.UserService;
import com.study.yaroslavambrozyak.simpleshop.validator.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private RegistrationValidator registrationValidator;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error",required = false)String error,Model model){
        if (error!=null){
            model.addAttribute("err","err");
        }
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user",new RegistrationUserDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationProcess(@ModelAttribute("user") RegistrationUserDTO userDTO, BindingResult bindingResult){
        registrationValidator.validate(userDTO,bindingResult);
        if (bindingResult.hasErrors())
            return "registration";
        userService.register(userDTO);
        return "redirect:/login";
    }
}
