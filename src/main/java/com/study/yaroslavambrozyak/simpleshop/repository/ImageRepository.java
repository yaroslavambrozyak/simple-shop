package com.study.yaroslavambrozyak.simpleshop.repository;

import com.study.yaroslavambrozyak.simpleshop.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
