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
    @OneToMany(mappedBy = "product")
    private List<Characteristics> characteristics;
    @ManyToOne
    private SubCategory category;
}
