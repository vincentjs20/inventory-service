package com.project.inventory.service.impl;

import com.project.inventory.dto.InventoryDTO;
import com.project.inventory.dto.response.InventoryResponse;
import com.project.inventory.model.Inventory;
import com.project.inventory.repository.InventoryRepository;
import com.project.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryResponse get(Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Inventory not found"));

        return InventoryResponse.builder()
                .id(inventory.getId())
                .item(inventory.getItem())
                .qty(inventory.getQty())
                .type(inventory.getType())
                .build();
    }

    @Override
    public Page<Inventory> list(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    @Override
    public InventoryResponse save(InventoryDTO inventory) {
        Inventory savedInventory = Inventory.builder()
                .type(inventory.getType())
                .item(inventory.getItem())
                .qty(inventory.getQty())
                .build();
        inventoryRepository.save(savedInventory);
        return InventoryResponse.builder()
                .id(savedInventory.getId())
                .item(savedInventory.getItem())
                .qty(savedInventory.getQty())
                .type(savedInventory.getType())
                .build();
    }

    @Override
    public InventoryResponse update(Long id, InventoryDTO inventory) {
        Inventory inventoryExisting = inventoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Inventory not found"));

        inventoryExisting.setQty(inventory.getQty());
        inventoryExisting.setType(inventory.getType());

        inventoryRepository.save(inventoryExisting);

        return InventoryResponse.builder()
                .id(inventoryExisting.getId())
                .item(inventoryExisting.getItem())
                .qty(inventoryExisting.getQty())
                .type(inventoryExisting.getType())
                .build();
    }

    @Override
    public void delete(Long id) {
        inventoryRepository.deleteById(id);
    }
}
