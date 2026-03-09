package com.project.inventory.service;

import com.project.inventory.dto.request.ItemRequest;
import com.project.inventory.dto.response.ItemResponse;
import com.project.inventory.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    ItemResponse get(Long id);

    Page<Item> list(Pageable pageable);

    ItemResponse save(ItemRequest item);

    ItemResponse update(Long id, ItemRequest item);

    String delete(Long id);

    Integer getRemainingStock(Long itemId);

}
