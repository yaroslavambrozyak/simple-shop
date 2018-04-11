package com.study.yaroslavambrozyak.simpleshop.dto;

import com.study.yaroslavambrozyak.simpleshop.entity.Characteristics;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availability;
    private Integer quantity;
    private List<MultipartFile> multipartFiles;
    private List<Characteristics> characteristics;

}
