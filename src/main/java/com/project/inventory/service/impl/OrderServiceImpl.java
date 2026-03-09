package com.project.inventory.service.impl;

import com.project.inventory.dto.OrderDTO;
import com.project.inventory.dto.request.UpdateOrderRequest;
import com.project.inventory.dto.response.OrderResponse;
import com.project.inventory.model.Order;
import com.project.inventory.repository.OrderRepository;
import com.project.inventory.service.ItemService;
import com.project.inventory.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository ordersRepository;
    private final ItemService itemService;

    @Override
    public OrderResponse get(String orderNo) {
        Order order = ordersRepository.findById(orderNo).orElseThrow(() -> new RuntimeException("Order not found"));

        return OrderResponse.builder()
                .orderNo(order.getOrderNo())
                .item(order.getItem())
                .qty(order.getQty())
                .price(order.getPrice())
                .build();
    }

    @Override
    public Page<Order> list(Pageable pageable) {
        return ordersRepository.findAll(pageable);
    }

    @Override
    public OrderResponse save(OrderDTO order) {

        Integer stock = itemService.getRemainingStock(order.getItem().getId());

        if (stock < order.getQty()) {
            throw new RuntimeException("Insufficient stock");
        }

        Order newOrder = Order.builder()
                .item(order.getItem())
                .qty(order.getQty())
                .price(order.getPrice())
                .build();

        ordersRepository.save(newOrder);

        return OrderResponse.builder()
                .orderNo(newOrder.getOrderNo())
                .item(newOrder.getItem())
                .qty(newOrder.getQty())
                .price(newOrder.getPrice())
                .build();
    }

    @Override
    public OrderResponse update(String orderNo, UpdateOrderRequest updateOrderRequest) {

        Order existing = ordersRepository.findById(orderNo).orElseThrow(() -> new RuntimeException("Order not found"));

        existing.setQty(updateOrderRequest.getQty());
        existing.setPrice(updateOrderRequest.getPrice());

        ordersRepository.save(existing);

        return OrderResponse.builder()
                .orderNo(existing.getOrderNo())
                .item(existing.getItem())
                .qty(existing.getQty())
                .price(existing.getPrice())
                .build();
    }

    @Override
    public void delete(String orderNo) {
        ordersRepository.deleteById(orderNo);
    }

}
