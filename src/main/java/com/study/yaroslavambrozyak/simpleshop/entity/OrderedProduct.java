package com.study.yaroslavambrozyak.simpleshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderedProduct {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    private Integer quantity;
}
