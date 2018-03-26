package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.dto.AcceptedOrderErrorDTO;
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

    @Override
    public List<AcceptedOrderErrorDTO> makeOrder(List<OrderedProductDTO> orderedProductsDTO) {
        List<AcceptedOrderErrorDTO> acceptedOrderDTOs = new ArrayList<>();
        List<OrderedProduct> orderedProducts = new ArrayList<>();
        orderedProductsDTO.forEach(orderedProductDTO -> {
            OrderedProduct orderedProduct = new OrderedProduct();
            Product product = null;
            try {
                product = productService.getProductForOrder(orderedProductDTO.getProductId()
                        , orderedProductDTO.getQuantity());
            } catch (Exception e) {
                acceptedOrderDTOs.add(new AcceptedOrderErrorDTO(orderedProductDTO.getName()));
            }
            orderedProduct.setProduct(product);
            orderedProduct.setQuantity(orderedProductDTO.getQuantity());
            orderedProducts.add(orderedProduct);
        });
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(userEmail);
        Order order = new Order();
        order.setOrderedProductList(orderedProducts);
        order.setUser(user);
        if (acceptedOrderDTOs.size() == 0)
            orderRepository.save(order);
        return acceptedOrderDTOs;
    }
}
