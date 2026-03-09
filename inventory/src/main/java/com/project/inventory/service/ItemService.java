package com.project.inventory.service;

import com.project.inventory.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    Item get(Long id);

    Page<Item> list(Pageable pageable);

    Item save(Item item);

    Item update(Long id, Item item);

    void delete(Long id);

    Integer getRemainingStock(Long itemId);

}
