package com.ram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ram.model.OrderItem;
import com.ram.repository.OrderItemRepository;
@Service
public class OrderItemServiceImpl implements OrderItemService {
	@Autowired
	 private OrderItemRepository orderItemRepository;

	    @Override
	    public OrderItem createOrderIem(OrderItem orderItem) {
	    	
	    	OrderItem newOrderItem=new OrderItem();
	    	newOrderItem.setQuantity(orderItem.getQuantity());
	        return orderItemRepository.save(newOrderItem);
	    }


}
