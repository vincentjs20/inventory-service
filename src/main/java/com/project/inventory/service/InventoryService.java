package com.project.inventory.service;

import com.project.inventory.dto.request.InventoryRequest;
import com.project.inventory.dto.request.UpdateInventoryRequest;
import com.project.inventory.dto.response.InventoryResponse;
import com.project.inventory.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {

    InventoryResponse get(Long id);

    Page<Inventory> list(Pageable pageable);

    InventoryResponse save(InventoryRequest inventory);

    InventoryResponse update(Long id, UpdateInventoryRequest inventoryRequest);

    String delete(Long id);

}
