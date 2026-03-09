package com.project.inventory.controller;

import com.project.inventory.dto.InventoryDTO;
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

@RestController
@RequestMapping("/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> get(@PathVariable Long id) {
        InventoryResponse response = inventoryService.get(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<Inventory>> list(Pageable pageable) {
        return ResponseEntity.ok(inventoryService.list(pageable));
    }

    @PostMapping
    public ResponseEntity<InventoryResponse> save(@RequestBody InventoryDTO inventory) {
        return ResponseEntity.ok(inventoryService.save(inventory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponse> update(@PathVariable Long id, @RequestBody InventoryDTO inventory) {
        return ResponseEntity.ok(inventoryService.update(id, inventory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        inventoryService.delete(id);
        return ResponseEntity.ok("Inventory Deleted.");
    }
}