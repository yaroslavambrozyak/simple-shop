package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.dto.OrderedProductDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.Order;
import com.study.yaroslavambrozyak.simpleshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

@Controller
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/order")
    public String getUserOrders(Model model) {
        List<Order> userOrder = orderService.getUserOrder();
        model.addAttribute("orders", userOrder);
        return "order";
    }

    //For release servlet thread! Is this right?
    @PostMapping("/order")
    public Callable<String> makeOrder(OrderedProductDTO orderedProducts, Model model) {
        return () -> {
            String status1 = orderService.makeOrder(orderedProducts);
            model.addAttribute("status", status1);
            return null;
        };
    }

}
