package com.study.yaroslavambrozyak.simpleshop.validator;

import com.study.yaroslavambrozyak.simpleshop.dto.PasswordResetDTO;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PasswordResetValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return PasswordResetDTO.class.equals(aClass);
    }

    @Override
    public void validate(@Nullable Object o, Errors errors) {
        PasswordResetDTO passwordResetDTO = (PasswordResetDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "validation.empty");
        if (passwordResetDTO.getPassword().length() < 8 || passwordResetDTO.getPassword().length() > 32)
            errors.rejectValue("password", "validation.registration.password.length");
        if (!passwordResetDTO.getPassword().equals(passwordResetDTO.getPasswordConfirm()))
            errors.rejectValue("passwordConfirm", "validation.registration.password.equality");
    }
}

