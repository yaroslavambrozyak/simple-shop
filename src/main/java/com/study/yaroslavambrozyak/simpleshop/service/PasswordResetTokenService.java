package com.study.yaroslavambrozyak.simpleshop.service;

import com.study.yaroslavambrozyak.simpleshop.entity.PasswordResetToken;
import com.study.yaroslavambrozyak.simpleshop.entity.User;

public interface PasswordResetTokenService {

    void create(User user,String token);

    PasswordResetToken get(String token);
}
