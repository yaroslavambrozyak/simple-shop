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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class AccountController {

    private UserService userService;
    private RegistrationValidator registrationValidator;
    private EmailService emailService;
    private PasswordResetTokenValidator passwordResetTokenValidator;
    private PasswordResetTokenService passwordResetTokenService;
    private PasswordResetValidator passwordResetValidator;
    private MessageSource messageSource;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @Autowired
    public AccountController(UserService userService, RegistrationValidator registrationValidator,
                             EmailService emailService, PasswordResetTokenValidator passwordResetTokenValidator,
                             PasswordResetTokenService passwordResetTokenService,
                             PasswordResetValidator passwordResetValidator, MessageSource messageSource) {
        this.userService = userService;
        this.registrationValidator = registrationValidator;
        this.emailService = emailService;
        this.passwordResetTokenValidator = passwordResetTokenValidator;
        this.passwordResetTokenService = passwordResetTokenService;
        this.passwordResetValidator = passwordResetValidator;
        this.messageSource = messageSource;
    }

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
           logger.error("error during login",e);
        }
        return "redirect:/";
    }

    @GetMapping("/resetPassword")
    public String resetPassword() {
        return "reset-password-email-form";
    }

    @PostMapping("/resetPassword")
    public String resetPasswordProcess(String email) {
        User user = userService.findUserByEmail(email);
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.create(user, token);
        emailService.sendMessage(user.getEmail(), prepareResetPasswordText(user.getId(), token));
        return "redirect:/resetEmail";
    }

    @GetMapping("/resetEmail")
    public String resetEmail() {
        return "reset-password-email-send";
    }

    @GetMapping("/reset")
    public String resetPassword(Model model, @RequestParam("id") Long id, @RequestParam("token") String token) {
        boolean isValid = passwordResetTokenValidator.validate(id, token);
        if (!isValid)
            return "reset-password-token-fail";
        model.addAttribute("change", new PasswordResetDTO());
        return "reset-password-new-password";
    }

    @PostMapping("/savePassword")
    public String savePassword(PasswordResetDTO passwordResetDTO, BindingResult bindingResult) {
        passwordResetValidator.validate(passwordResetDTO, bindingResult);
        if (bindingResult.hasErrors())
            return "reset-password-new-password";
        userService.changePassword(passwordResetDTO.getPassword());
        return "redirect:/";
    }

    private boolean isAnonymous() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken;
    }

    private String prepareResetPasswordText(Long id, String token) {
        String url = "/reset?id=" + id + "&token=" + token;
        return messageSource.getMessage("reset-password.email"
                ,new Object[]{url}, LocaleContextHolder.getLocale());
    }

}
