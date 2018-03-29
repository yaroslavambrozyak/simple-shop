package com.study.yaroslavambrozyak.simpleshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryDTO {

    private long id;
    private String name;
    private Long rootCategoryId;
}
