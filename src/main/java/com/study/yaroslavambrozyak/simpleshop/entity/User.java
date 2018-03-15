package com.study.yaroslavambrozyak.simpleshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_acc")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
}
