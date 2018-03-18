package com.study.yaroslavambrozyak.simpleshop.validator;


import com.study.yaroslavambrozyak.simpleshop.dto.RegistrationUserDTO;
import com.study.yaroslavambrozyak.simpleshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationUserDTO.class.equals(aClass);
    }

    @Override
    public void validate(@Nullable Object o, Errors errors) {
        RegistrationUserDTO userDTO = (RegistrationUserDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email","NotEmpty");
        if (userService.findUserByEmail(userDTO.getEmail())!=null)
            errors.rejectValue("email","duplicate");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","NotEmpty");
        if (userDTO.getPassword().length()<8 || userDTO.getPassword().length()>32)
            errors.rejectValue("password","length");
        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm()))
            errors.rejectValue("passwordConfirm","not eq");
    }
}
