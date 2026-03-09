package com.project.inventory.controller;

import com.project.inventory.dto.request.OrderRequest;
import com.project.inventory.dto.request.UpdateOrderRequest;
import com.project.inventory.dto.response.OrderResponse;
import com.project.inventory.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderNo}")
    public ResponseEntity<?> get(@PathVariable String orderNo) {
        try {
            OrderResponse order = orderService.get(orderNo);
            return ResponseEntity.ok(order);
        }
        catch (RuntimeException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> list(Pageable pageable) {
        try {
            return ResponseEntity.ok(orderService.list(pageable));
        }
        catch (RuntimeException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OrderRequest orderRequest) {
        try {
            OrderResponse savedOrder = orderService.save(orderRequest);
            return ResponseEntity.ok(savedOrder);
        }
        catch (RuntimeException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @PutMapping("/{orderNo}")
    public ResponseEntity<?> update(@PathVariable String orderNo,
                                    @RequestBody UpdateOrderRequest request) {
        try {
            OrderResponse updatedOrder = orderService.update(orderNo, request);
            return ResponseEntity.ok(updatedOrder);
        }
        catch (RuntimeException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @DeleteMapping("/{orderNo}")
    public ResponseEntity<?> delete(@PathVariable String orderNo) {
        try {
            String message = orderService.delete(orderNo);
            return ResponseEntity.ok(message);
        }
        catch (RuntimeException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

}