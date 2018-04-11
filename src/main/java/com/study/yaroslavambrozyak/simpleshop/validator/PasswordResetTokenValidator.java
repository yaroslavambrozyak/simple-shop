package com.study.yaroslavambrozyak.simpleshop.validator;

import com.study.yaroslavambrozyak.simpleshop.entity.PasswordResetToken;
import com.study.yaroslavambrozyak.simpleshop.entity.User;
import com.study.yaroslavambrozyak.simpleshop.exception.PasswordTokenException;
import com.study.yaroslavambrozyak.simpleshop.repository.PasswordResetTokenRepository;
import com.study.yaroslavambrozyak.simpleshop.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.Objects;

@Component
public class PasswordResetTokenValidator {

    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public PasswordResetTokenValidator(PasswordResetTokenService passwordResetTokenService) {
        this.passwordResetTokenService = passwordResetTokenService;
    }

    public boolean validate(Long id,String token){
        PasswordResetToken passToken = passwordResetTokenService.get(token);
        if(!Objects.equals(passToken.getUser().getId(), id))
            return false;
        if(!passToken.getExpiryDate().after(new Date()))
            return false;
        User user = passToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(user,null,
                Collections.singletonList(new SimpleGrantedAuthority("CHANGE_PASSWORD")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return true;
    }

}
