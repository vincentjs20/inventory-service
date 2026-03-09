package com.project.inventory.service.impl;

import com.project.inventory.dto.request.OrderRequest;
import com.project.inventory.dto.request.UpdateOrderRequest;
import com.project.inventory.dto.response.OrderResponse;
import com.project.inventory.model.Inventory;
import com.project.inventory.model.Item;
import com.project.inventory.model.Order;
import com.project.inventory.repository.InventoryRepository;
import com.project.inventory.repository.ItemRepository;
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

    private final ItemRepository itemRepository;

    private final InventoryRepository inventoryRepository;

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
    public OrderResponse save(OrderRequest order) {
        Item existingItem = itemRepository.findById(order.getItemId()).orElseThrow(()-> new RuntimeException("Item not found"));

        Integer stock = itemService.getRemainingStock(order.getItemId());

        if (stock < order.getQty()) {
            throw new RuntimeException("Insufficient stock");
        }

        Long count = ordersRepository.countOrders();

        String orderNo = "O" + (count + 1);

        Order newOrder = Order.builder()
                .orderNo(orderNo)
                .item(itemRepository.findById(order.getItemId()).orElseThrow(()-> new RuntimeException("Item not found")))
                .qty(order.getQty())
                .price(existingItem.getPrice())
                .build();

        Inventory newInventory = Inventory.builder()
                .item(existingItem)
                .qty(order.getQty())
                .type("W")
                .build();

        inventoryRepository.save(newInventory);

        newOrder.setInventory(newInventory);

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

        Order existingOrder = ordersRepository.findById(orderNo).orElseThrow(() -> new RuntimeException("Order not found"));

        Integer currentStock = itemService.getRemainingStock(existingOrder.getItem().getId()) + existingOrder.getInventory().getQty();

        if (currentStock - updateOrderRequest.getQty() < 0) {
            throw new RuntimeException("Insufficient stock");
        }

        existingOrder.setQty(updateOrderRequest.getQty());

        ordersRepository.save(existingOrder);

        existingOrder.getInventory().setQty(updateOrderRequest.getQty());

        inventoryRepository.save(existingOrder.getInventory());

        return OrderResponse.builder()
                .orderNo(existingOrder.getOrderNo())
                .item(existingOrder.getItem())
                .qty(existingOrder.getQty())
                .price(existingOrder.getPrice())
                .build();
    }

    @Override
    public String delete(String orderNo) {

        Order currentOrder = ordersRepository.findById(orderNo).orElseThrow(() -> new RuntimeException("Order not found"));
        Inventory inventory = currentOrder.getInventory();

        ordersRepository.deleteById(orderNo);

        inventoryRepository.deleteById(inventory.getId());

        return String.format("Item with id : %s deleted successfully", orderNo);
    }

}
