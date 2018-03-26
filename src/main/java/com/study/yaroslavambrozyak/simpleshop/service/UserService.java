package com.study.yaroslavambrozyak.simpleshop.service;

import com.study.yaroslavambrozyak.simpleshop.dto.RegistrationUserDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.User;

public interface UserService {

    User findUserByEmail(String email);

    void register(RegistrationUserDTO userDTO);

    void changePassword(String password);
}
