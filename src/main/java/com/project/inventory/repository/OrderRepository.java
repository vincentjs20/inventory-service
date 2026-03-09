package com.project.inventory.repository;

import com.project.inventory.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, String> {

    @Query("SELECT COUNT(o) FROM Order o")
    Long countOrders();

}