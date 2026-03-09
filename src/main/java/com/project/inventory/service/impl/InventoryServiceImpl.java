package com.project.inventory.service.impl;

import com.project.inventory.dto.request.InventoryRequest;
import com.project.inventory.dto.request.UpdateInventoryRequest;
import com.project.inventory.dto.response.InventoryResponse;
import com.project.inventory.model.Inventory;
import com.project.inventory.repository.InventoryRepository;
import com.project.inventory.repository.ItemRepository;
import com.project.inventory.service.InventoryService;
import com.project.inventory.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final ItemRepository itemRepository;

    private final ItemService itemService;

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
    public InventoryResponse save(InventoryRequest inventory) {
        Integer stock = itemService.getRemainingStock(inventory.getItemId());

        if (inventory.getType().equals("W") && stock < inventory.getQty()) {
            throw new RuntimeException("Insufficient stock");
        }

        Inventory savedInventory = Inventory.builder()
                .type(inventory.getType())
                .item(itemRepository.findById(inventory.getItemId()).orElseThrow(()-> new RuntimeException("Item not found")))
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
    public InventoryResponse update(Long id, UpdateInventoryRequest inventoryRequest) {
        Inventory inventoryExisting = inventoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Inventory not found"));

        Integer currentStock = itemService.getRemainingStock(id) - inventoryExisting.getQty();

        if (inventoryRequest.getType().equals("W") && (currentStock - inventoryRequest.getQty() < 0)) {
            throw new RuntimeException("Insufficient stock");
        }

        inventoryExisting.setQty(inventoryRequest.getQty());
        inventoryExisting.setType(inventoryRequest.getType());

        inventoryRepository.save(inventoryExisting);

        return InventoryResponse.builder()
                .id(inventoryExisting.getId())
                .item(inventoryExisting.getItem())
                .qty(inventoryExisting.getQty())
                .type(inventoryExisting.getType())
                .build();
    }

    @Override
    public String delete(Long id) {

        Inventory currentInventory = inventoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Inventory not found"));
        int currentStock = itemService.getRemainingStock(id) - currentInventory.getQty();
        if (currentInventory.getType().equals("T") && (currentStock < 0)) {
            throw new RuntimeException("Insufficient stock");
        }
        inventoryRepository.deleteById(id);
        return String.format("Inventory with id : %d deleted successfully", id);
    }
}
