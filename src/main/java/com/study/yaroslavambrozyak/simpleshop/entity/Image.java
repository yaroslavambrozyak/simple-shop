package com.study.yaroslavambrozyak.simpleshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
@Setter
@Getter
public class Image {

    private String path;
    private Integer position;

}
