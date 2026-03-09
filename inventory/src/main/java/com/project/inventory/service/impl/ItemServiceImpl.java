package com.project.inventory.service.impl;

import com.project.inventory.model.Item;
import com.project.inventory.repository.InventoryRepository;
import com.project.inventory.repository.ItemRepository;
import com.project.inventory.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final InventoryRepository inventoryRepository;

    @Override
    public Item get(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @Override
    public Page<Item> list(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item update(Long id, Item item) {
        Item existing = get(id);

        existing.setName(item.getName());
        existing.setPrice(item.getPrice());

        return itemRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Integer getRemainingStock(Long itemId) {
        Integer topUp = inventoryRepository.sumByItemAndType(itemId, "T");
        Integer withdrawal = inventoryRepository.sumByItemAndType(itemId, "W");

        if (topUp == null) topUp = 0;
        if (withdrawal == null) withdrawal = 0;

        return topUp - withdrawal;
    }
}
