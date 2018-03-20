package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.dto.OrderedProductDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.Order;
import com.study.yaroslavambrozyak.simpleshop.entity.OrderedProduct;
import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import com.study.yaroslavambrozyak.simpleshop.entity.User;
import com.study.yaroslavambrozyak.simpleshop.repository.OrderRepository;
import com.study.yaroslavambrozyak.simpleshop.service.OrderService;
import com.study.yaroslavambrozyak.simpleshop.service.ProductService;
import com.study.yaroslavambrozyak.simpleshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @Override
    public List<Order> getUserOrder() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderRepository.findAllByUser_Email(email);
    }

    // TODO logic? checks? O_o
    @Override
    public void makeOrder(List<OrderedProductDTO> orderedProductsDTO) {
        List<OrderedProduct> orderedProducts = new ArrayList<>();
        orderedProductsDTO.forEach(orderedProductDTO -> {
            OrderedProduct orderedProduct = new OrderedProduct();
            orderedProduct.setProduct(productService.getProduct(orderedProductDTO.getProductId()));
            orderedProduct.setQuantity(orderedProductDTO.getQuantity());
        });
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(userEmail);
        Order order = new Order();
        order.setOrderedProductList(orderedProducts);
        order.setUser(user);
        orderRepository.save(order);
    }
}
