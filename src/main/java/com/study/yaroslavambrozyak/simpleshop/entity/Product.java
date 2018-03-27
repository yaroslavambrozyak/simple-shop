package com.study.yaroslavambrozyak.simpleshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availability;
    private Integer quantity;
    @OneToMany(mappedBy = "product")
    private List<Characteristics> characteristics;
    @OneToMany(mappedBy = "product")
    private List<Image> images;
    @ManyToOne
    private SubCategory category;
}
