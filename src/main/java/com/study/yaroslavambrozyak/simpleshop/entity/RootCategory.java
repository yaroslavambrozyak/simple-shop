package com.study.yaroslavambrozyak.simpleshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class RootCategory {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String imagePath;
    @OneToMany(mappedBy = "rootCategory")
    private Set<SubCategory> subCategories;
}
