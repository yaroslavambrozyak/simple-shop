package com.study.yaroslavambrozyak.simpleshop.service;

import com.study.yaroslavambrozyak.simpleshop.dto.OrderedProductDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getUserOrder();

    void makeOrder(List<OrderedProductDTO> orderedProduct);

}
