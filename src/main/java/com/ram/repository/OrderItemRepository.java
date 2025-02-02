package com.ram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ram.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
