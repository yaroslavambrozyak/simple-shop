package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.dto.PasswordResetDTO;
import com.study.yaroslavambrozyak.simpleshop.dto.RegistrationUserDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.User;
import com.study.yaroslavambrozyak.simpleshop.service.EmailService;
import com.study.yaroslavambrozyak.simpleshop.service.PasswordResetTokenService;
import com.study.yaroslavambrozyak.simpleshop.service.UserService;
import com.study.yaroslavambrozyak.simpleshop.validator.PasswordResetTokenValidator;
import com.study.yaroslavambrozyak.simpleshop.validator.PasswordResetValidator;
import com.study.yaroslavambrozyak.simpleshop.validator.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private RegistrationValidator registrationValidator;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordResetTokenValidator passwordResetTokenValidator;
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @Autowired
    private PasswordResetValidator passwordResetValidator;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (!isAnonymous())
            return "redirect:/";
        if (error != null && !error.isEmpty())
            model.addAttribute("err", "validation.login.fail");
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if (!isAnonymous())
            return "redirect:/";
        model.addAttribute("reg", new RegistrationUserDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationProcess(@ModelAttribute("reg") RegistrationUserDTO userDTO, BindingResult bindingResult,
                                      HttpServletRequest request) {
        registrationValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors())
            return "registration";
        userService.register(userDTO);
        try {
            request.login(userDTO.getEmail(), userDTO.getPassword());
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/resetPassword")
    public String resetPassword() {
        return "reset-password-email-form";
    }

    @PostMapping("/resetPassword")
    public String resetPasswordProcess(String email, HttpServletRequest request) {
        User user = userService.findUserByEmail(email);
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.create(user, token);
        emailService.sendMessage(user.getEmail(), prepareResetPasswordText(request.getContextPath(), user.getId(), token));
        return "redirect:/resetEmail";
    }

    @GetMapping("/resetEmail")
    public String resetEmail(){
        return "reset-password-email-send";
    }

    @GetMapping("/reset")
    public String resetPassword(Model model,@RequestParam("id") Long id, @RequestParam("token") String token) {
        boolean isValid = passwordResetTokenValidator.validate(id, token);
        if(!isValid)
            return "reset-password-token-fail";
        model.addAttribute("change",new PasswordResetDTO());
        return "reset-password-new-password";
    }

    @PostMapping("/savePassword")
    public String savePassword(PasswordResetDTO passwordResetDTO, BindingResult bindingResult, HttpServletRequest request,
                               HttpServletResponse response){
        passwordResetValidator.validate(passwordResetDTO,bindingResult);
        if (bindingResult.hasErrors())
            return "reset-password-new-password";
        userService.changePassword(passwordResetDTO.getPassword());
        logout(request,response);
        return "redirect:/login";
    }

    private boolean isAnonymous() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken;
    }

    private String prepareResetPasswordText(String contextPath, Long id, String token) {
        String preUrl = "lalala click url lalala";
        String url = contextPath + "/reset?id=" + id + "&token=" + token;
        return preUrl + "\n" + url;
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
