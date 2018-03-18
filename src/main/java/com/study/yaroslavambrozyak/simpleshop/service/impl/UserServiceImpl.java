package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.dto.UserDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.User;
import com.study.yaroslavambrozyak.simpleshop.exception.NotFoundException;
import com.study.yaroslavambrozyak.simpleshop.repository.UserRepository;
import com.study.yaroslavambrozyak.simpleshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //TODO roles
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(NotFoundException::new);
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword()
                , Collections.emptyList());
    }


    @Override
    public void register(UserDTO userDTO){
        User user = modelMapper.map(userDTO,User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }
}
