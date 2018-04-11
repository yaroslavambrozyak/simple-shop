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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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

    private final String LOGIN_VIEW = "login";
    private final String REGISTRATION_VIEW = "registration";
    private final String RESET_PASSWORD_FORM = "reset-password-email-form";
    private final String RESET_PASSWORD_EMAIL_CONFIRM_VIEW = "reset-password-email-send";
    private final String RESET_PASSWORD_TOKEN_FAIL_VIEW = "reset-password-token-fail";
    private final String RESET_PASSWORD_TOKEN_SUCCESS_VIEW = "reset-password-new-password";

    private final String REDIRECT_MAIN = "redirect:/";
    private final String REDIRECT_EMAIL_CONFIRM = "redirect:/resetEmail";


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
            return REDIRECT_MAIN;
        if (error != null && !error.isEmpty())
            model.addAttribute("err", "validation.login.fail");
        return LOGIN_VIEW;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if (!isAnonymous())
            return REDIRECT_MAIN;
        model.addAttribute("reg", new RegistrationUserDTO());
        return REGISTRATION_VIEW;
    }

    @PostMapping("/registration")
    public String registrationProcess(@ModelAttribute("reg") RegistrationUserDTO userDTO, BindingResult bindingResult,
                                      HttpServletRequest request) {
        registrationValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors())
            return REGISTRATION_VIEW;
        userService.register(userDTO);
        try {
            request.login(userDTO.getEmail(), userDTO.getPassword());
        } catch (ServletException e) {
            logger.error("error during login", e);
        }
        return REDIRECT_MAIN;
    }

    @GetMapping("/resetPassword")
    public String resetPassword() {
        return RESET_PASSWORD_FORM;
    }

    @PostMapping("/resetPassword")
    public String resetPasswordProcess(String email) {
        User user = userService.findUserByEmail(email);
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.create(user, token);
        emailService.sendMessage(user.getEmail(), prepareResetPasswordText(user.getId(), token));
        return REDIRECT_EMAIL_CONFIRM;
    }

    @GetMapping("/resetEmail")
    public String resetEmail() {
        return RESET_PASSWORD_EMAIL_CONFIRM_VIEW;
    }

    @GetMapping("/reset")
    public String resetPassword(Model model, @RequestParam("id") Long id, @RequestParam("token") String token) {
        boolean isValid = passwordResetTokenValidator.validate(id, token);
        if (!isValid)
            return RESET_PASSWORD_TOKEN_FAIL_VIEW;
        model.addAttribute("change", new PasswordResetDTO());
        return RESET_PASSWORD_TOKEN_SUCCESS_VIEW;
    }

    @PostMapping("/savePassword")
    public String savePassword(PasswordResetDTO passwordResetDTO, BindingResult bindingResult) {
        passwordResetValidator.validate(passwordResetDTO, bindingResult);
        if (bindingResult.hasErrors())
            return RESET_PASSWORD_TOKEN_SUCCESS_VIEW;
        userService.changePassword(passwordResetDTO.getPassword());
        return REDIRECT_MAIN;
    }

    private boolean isAnonymous() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken;
    }

    private String prepareResetPasswordText(Long id, String token) {
        String url = "/reset?id=" + id + "&token=" + token;
        return messageSource.getMessage("reset-password.email"
                , new Object[]{url}, LocaleContextHolder.getLocale());
    }

}
