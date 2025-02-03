package com.ram.service;

import java.util.List;

import com.ram.Exception.CartException;
import com.ram.Exception.OrderException;
import com.ram.Exception.RestaurantException;
import com.ram.Exception.UserException;
import com.ram.model.Order;
import com.ram.model.User;
import com.ram.request.CreateOrderRequest;

public interface OrderService {
	
	 public String createOrder(CreateOrderRequest order, User user) throws UserException, RestaurantException, CartException;
	 

	 
	 public void cancelOrder(Long orderId) throws OrderException;
	 
	 public List<Order> getUserOrders(Long userId) throws OrderException;
	 
	 public List<Order> getOrdersOfRestaurant(Long restaurantId) throws OrderException, RestaurantException;
	 

}
