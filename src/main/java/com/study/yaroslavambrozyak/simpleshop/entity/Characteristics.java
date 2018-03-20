package com.study.yaroslavambrozyak.simpleshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Characteristics {

    @Id
    private Long id;
    private String characteristicsKey;
    private String characteristicsValue;
    @ManyToOne
    private Product product;

}
