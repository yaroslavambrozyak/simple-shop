package com.study.yaroslavambrozyak.simpleshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Long price;
    @ManyToOne
    private SubCategory category;
}
