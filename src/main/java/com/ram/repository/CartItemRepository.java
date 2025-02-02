package com.ram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ram.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


//    CartItem findByFoodIsContaining

}
