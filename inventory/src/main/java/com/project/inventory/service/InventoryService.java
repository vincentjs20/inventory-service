package com.project.inventory.service;

import com.project.inventory.dto.InventoryDTO;
import com.project.inventory.dto.response.InventoryResponse;
import com.project.inventory.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {

    public InventoryResponse get(Long id);

    public Page<Inventory> list(Pageable pageable);

    public InventoryResponse save(InventoryDTO inventory);

    public InventoryResponse update(Long id, InventoryDTO inventory);

    public void delete(Long id);

}
