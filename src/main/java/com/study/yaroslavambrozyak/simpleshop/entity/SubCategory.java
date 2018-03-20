package com.study.yaroslavambrozyak.simpleshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class SubCategory {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String imagePath;
    @ManyToOne
    private RootCategory rootCategory;
    @OneToMany(mappedBy = "category")
    private Set<Product> products;
}
