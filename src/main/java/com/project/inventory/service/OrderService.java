package com.project.inventory.service;

import com.project.inventory.dto.request.OrderRequest;
import com.project.inventory.dto.request.UpdateOrderRequest;
import com.project.inventory.dto.response.OrderResponse;
import com.project.inventory.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponse get(String orderNo);

    Page<Order> list(Pageable pageable);

    OrderResponse save(OrderRequest order);

    OrderResponse update(String orderNo, UpdateOrderRequest updateOrderRequest);

    String delete(String orderNo);
}