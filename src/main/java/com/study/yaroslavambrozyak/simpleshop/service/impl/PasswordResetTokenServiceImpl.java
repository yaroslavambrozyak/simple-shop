package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.entity.PasswordResetToken;
import com.study.yaroslavambrozyak.simpleshop.entity.User;
import com.study.yaroslavambrozyak.simpleshop.repository.PasswordResetTokenRepository;
import com.study.yaroslavambrozyak.simpleshop.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    private static final int EXPIRATION = 60 * 24;

    @Override
    public void create(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        long expirationTime = new Date().getTime() + EXPIRATION;
        passwordResetToken.setExpiryDate(new Date(expirationTime));
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public PasswordResetToken get(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
}
