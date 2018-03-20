package com.study.yaroslavambrozyak.simpleshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderedProductDTO {
    private Long productId;
    private Integer quantity;
}
