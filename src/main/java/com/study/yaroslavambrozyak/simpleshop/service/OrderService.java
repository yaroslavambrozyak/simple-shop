package com.study.yaroslavambrozyak.simpleshop.service;

import com.study.yaroslavambrozyak.simpleshop.dto.OrderedProductDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.Order;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface OrderService {

    @PreAuthorize("hasRole('ROLE_USER')")
    List<Order> getUserOrder();

    @PreAuthorize("hasRole('ROLE_USER')")
    String makeOrder(OrderedProductDTO orderedProduct);


}
