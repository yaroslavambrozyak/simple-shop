package com.study.yaroslavambrozyak.simpleshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;


@Entity
@Getter
@Setter
public class PasswordResetToken {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(mappedBy = "passwordResetToken")
    private User user;
    private String token;
    private Date expiryDate;
}
