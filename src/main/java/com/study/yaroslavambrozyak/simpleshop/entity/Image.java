package com.study.yaroslavambrozyak.simpleshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
public class Image {

    @Id
    @GeneratedValue
    private Long id;
    private String path;
    private Integer position;
    @ManyToOne
    private Product product;
}
