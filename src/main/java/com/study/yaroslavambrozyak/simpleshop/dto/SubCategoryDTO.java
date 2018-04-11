package com.study.yaroslavambrozyak.simpleshop.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SubCategoryDTO {

    private long id;
    private String name;
    private Long rootCategoryId;
    private MultipartFile image;
}
