package com.project.inventory.controller;

import com.project.inventory.dto.request.InventoryRequest;
import com.project.inventory.dto.request.UpdateInventoryRequest;
import com.project.inventory.dto.response.InventoryResponse;
import com.project.inventory.model.Inventory;
import com.project.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
@RequestMapping("/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            InventoryResponse response = inventoryService.get(id);
            return ResponseEntity.ok(response);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> list(Pageable pageable) {
        try {
            Page<Inventory> inventory = inventoryService.list(pageable);
            return ResponseEntity.ok(inventory);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody InventoryRequest inventory) {
        try {
            InventoryResponse savedInventory = inventoryService.save(inventory);
            return ResponseEntity.ok(savedInventory);
        }
        catch (RuntimeException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateInventoryRequest inventory) {
        try {
            InventoryResponse savedInventory = inventoryService.update(id, inventory);
            return ResponseEntity.ok(savedInventory);
        }
        catch (RuntimeException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            String response = inventoryService.delete(id);
            return ResponseEntity.ok(response);
        }
        catch (RuntimeException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }
}