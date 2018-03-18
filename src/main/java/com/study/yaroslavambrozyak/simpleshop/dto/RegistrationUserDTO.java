package com.study.yaroslavambrozyak.simpleshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationUserDTO {

    private String email;
    private String password;
    private String passwordConfirm;

}
