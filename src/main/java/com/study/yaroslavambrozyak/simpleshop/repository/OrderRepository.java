package com.study.yaroslavambrozyak.simpleshop.repository;

import com.study.yaroslavambrozyak.simpleshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByUser_Email(String email);
}
