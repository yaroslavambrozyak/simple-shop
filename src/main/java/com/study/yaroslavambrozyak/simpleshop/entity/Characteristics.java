package com.study.yaroslavambrozyak.simpleshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Embeddable
@Getter
@Setter
public class Characteristics {

    private String characteristicsKey;
    private String characteristicsValue;

}
