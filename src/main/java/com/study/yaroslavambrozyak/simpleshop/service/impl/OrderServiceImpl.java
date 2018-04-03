package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.dto.OrderedProductDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.Order;
import com.study.yaroslavambrozyak.simpleshop.entity.OrderedProduct;
import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import com.study.yaroslavambrozyak.simpleshop.entity.User;
import com.study.yaroslavambrozyak.simpleshop.exception.NotEnoughException;
import com.study.yaroslavambrozyak.simpleshop.repository.OrderRepository;
import com.study.yaroslavambrozyak.simpleshop.service.OrderService;
import com.study.yaroslavambrozyak.simpleshop.service.ProductService;
import com.study.yaroslavambrozyak.simpleshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private ProductService productService;
    private UserService userService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService,
                            UserService userService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> getUserOrder() {
        String email = userService.getCurrentUser().getEmail();
        return orderRepository.findAllByUser_Email(email);
    }

    @Override
    public String makeOrder(OrderedProductDTO orderedProductDTO) {
        try {
            Product product = productService.getProductForOrder(orderedProductDTO.getProductId()
                    ,orderedProductDTO.getQuantity());
            OrderedProduct orderedProduct = new OrderedProduct();
            orderedProduct.setProduct(product);
            orderedProduct.setQuantity(orderedProductDTO.getQuantity());
            productService.subtractQuantity(product,orderedProductDTO.getQuantity());
            User user = userService.getCurrentUser();
            Order order = new Order();
            order.setOrderedProduct(orderedProduct);
            order.setUser(user);
            orderRepository.save(order);
        } catch (NotEnoughException e) {
            return "Not enough";
        }
        return "Success";
    }
}
