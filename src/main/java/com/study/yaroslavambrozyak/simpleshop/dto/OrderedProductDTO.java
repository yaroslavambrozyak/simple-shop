package com.study.yaroslavambrozyak.simpleshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderedProductDTO {
    private long productId;
    private String name;
    private Integer quantity;
}
