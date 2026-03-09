package com.project.inventory.service;

import com.project.inventory.dto.ItemDTO;
import com.project.inventory.dto.response.ItemResponse;
import com.project.inventory.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    ItemResponse get(Long id);

    Page<Item> list(Pageable pageable);

    ItemResponse save(ItemDTO item);

    ItemResponse update(Long id, ItemDTO item);

    void delete(Long id);

    Integer getRemainingStock(Long itemId);

}
