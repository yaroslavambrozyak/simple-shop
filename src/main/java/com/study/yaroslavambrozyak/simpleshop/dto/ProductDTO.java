package com.study.yaroslavambrozyak.simpleshop.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availability;
    private Integer quantity;

}
