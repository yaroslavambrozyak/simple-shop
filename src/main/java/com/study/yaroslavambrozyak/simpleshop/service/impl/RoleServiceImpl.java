package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.entity.Role;
import com.study.yaroslavambrozyak.simpleshop.repository.RoleRepository;
import com.study.yaroslavambrozyak.simpleshop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private final String DEFAULT_ROLE_NAME = "ROLE_USER";

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getDefaultRole() {
        return roleRepository.getByRoleName(DEFAULT_ROLE_NAME);
    }
}
