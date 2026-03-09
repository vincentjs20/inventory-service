package com.project.inventory.service.impl;

import com.project.inventory.dto.ItemDTO;
import com.project.inventory.dto.response.ItemResponse;
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
    public ItemResponse get(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(()-> new RuntimeException("Item not found"));
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .build();
    }

    @Override
    public Page<Item> list(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    public ItemResponse save(ItemDTO item) {
        Item newItem = Item.builder()
                .name(item.getName())
                .price(item.getPrice())
                .build();
        itemRepository.save(newItem);
        return ItemResponse.builder()
                .id(newItem.getId())
                .name(newItem.getName())
                .price(newItem.getPrice())
                .build();
    }

    @Override
    public ItemResponse update(Long id, ItemDTO item) {
        Item existing = itemRepository.findById(id).orElseThrow(()-> new RuntimeException("Item not found"));

        existing.setName(item.getName());
        existing.setPrice(item.getPrice());

        itemRepository.save(existing);

        return ItemResponse.builder()
                .id(existing.getId())
                .name(existing.getName())
                .price(existing.getPrice())
                .build();
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
