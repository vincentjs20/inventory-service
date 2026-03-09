package com.project.inventory.controller;

import com.project.inventory.dto.OrderDTO;
import com.project.inventory.dto.request.UpdateOrderRequest;
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

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderNo}")
    public ResponseEntity<?> get(@PathVariable String orderNo) {
        return ResponseEntity.ok(orderService.get(orderNo));
    }

    @GetMapping
    public ResponseEntity<?> list(Pageable pageable) {
        return ResponseEntity.ok(orderService.list(pageable));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.save(orderDTO));
    }

    @PutMapping("/{orderNo}")
    public ResponseEntity<?> update(@PathVariable String orderNo,
                                    @RequestBody UpdateOrderRequest request) {
        return ResponseEntity.ok(orderService.update(orderNo, request));
    }

    @DeleteMapping("/{orderNo}")
    public ResponseEntity<?> delete(@PathVariable String orderNo) {
        orderService.delete(orderNo);
        return ResponseEntity.ok("Order deleted successfully");
    }

}