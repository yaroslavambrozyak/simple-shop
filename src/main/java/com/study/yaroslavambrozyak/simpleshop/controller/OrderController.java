package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.dto.OrderedProductDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.Order;
import com.study.yaroslavambrozyak.simpleshop.entity.OrderedProduct;
import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import com.study.yaroslavambrozyak.simpleshop.service.OrderService;
import com.study.yaroslavambrozyak.simpleshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @GetMapping("/order")
    public String getUserOrders(Model model){
        List<Order> userOrder = orderService.getUserOrder();
        model.addAttribute("orders",userOrder);
        return "order";
    }

    @PostMapping("/order")
    public String makeOrder(List<OrderedProductDTO> orderedProducts, Model model){
        orderService.makeOrder(orderedProducts);
        return null;
    }

}