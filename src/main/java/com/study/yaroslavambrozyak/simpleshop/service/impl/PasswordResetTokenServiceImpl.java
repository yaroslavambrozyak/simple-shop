package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.entity.PasswordResetToken;
import com.study.yaroslavambrozyak.simpleshop.entity.User;
import com.study.yaroslavambrozyak.simpleshop.exception.PasswordTokenException;
import com.study.yaroslavambrozyak.simpleshop.repository.PasswordResetTokenRepository;
import com.study.yaroslavambrozyak.simpleshop.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private PasswordResetTokenRepository passwordResetTokenRepository;
    private static final int EXPIRATION = 600000; //10 min

    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

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
        return passwordResetTokenRepository.findByToken(token)
                .orElseThrow(PasswordTokenException::new);
    }
}
