package com.project.inventory.repository;

import com.project.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("""
           SELECT SUM(i.qty)
           FROM Inventory i
           WHERE i.item.id = :itemId
           AND i.type = :type
           """)
    Integer sumByItemAndType(Long itemId, String type);

}
