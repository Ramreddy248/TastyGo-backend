package com.ram.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ram.Exception.CartException;
import com.ram.Exception.UserException;
import com.ram.model.*;
import com.ram.request.CreateOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ram.Exception.OrderException;
import com.ram.Exception.RestaurantException;
import com.ram.repository.AddressRepository;
import com.ram.repository.OrderItemRepository;
import com.ram.repository.OrderRepository;
import com.ram.repository.RestaurantRepository;
import com.ram.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CartSerive cartService;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private UserRepository userRepository;



	@Override
	public String createOrder(CreateOrderRequest order, User user) throws UserException, RestaurantException, CartException {

		Address  shippAddress = order.getDeliveryAddress();


		Address savedAddress = addressRepository.save(shippAddress);

		if(!user.getAddresses().contains(savedAddress)) {
			user.getAddresses().add(savedAddress);
		}


		System.out.println("user addresses --------------  "+user.getAddresses());

		userRepository.save(user);

		Optional<Restaurant> restaurant = restaurantRepository.findById(order.getRestaurantId());
		if(restaurant.isEmpty()) {
			throw new RestaurantException("Restaurant not found with id "+order.getRestaurantId());
		}

		Order createdOrder = new Order();

		createdOrder.setCustomer(user);
		createdOrder.setDeliveryAddress(savedAddress);
		createdOrder.setCreatedAt(new Date());
		createdOrder.setRestaurant(restaurant.get());

		Cart cart = cartService.findCartByUserId(user.getId());

		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItem cartItem : cart.getItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setFood(cartItem.getFood());

			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalPrice(cartItem.getFood().getPrice()* cartItem.getQuantity());

			OrderItem savedOrderItem = orderItemRepository.save(orderItem);
			orderItems.add(savedOrderItem);
		}

		Long totalPrice = cartService.calculateCartTotals(cart);

		createdOrder.setTotalAmount(totalPrice);
		createdOrder.setRestaurant(restaurant.get());

		createdOrder.setItems(orderItems);
		Order savedOrder = orderRepository.save(createdOrder);

		restaurant.get().getOrders().add(savedOrder);

		restaurantRepository.save(restaurant.get());


		return "Order Success";

	}





	@Override
	public void cancelOrder(Long orderId) throws OrderException {
           Order order =findOrderById(orderId);
           if(order==null) {
        	   throw new OrderException("Order not found with the id "+orderId);
           }
		
		    orderRepository.deleteById(orderId);
	}
	
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> order = orderRepository.findById(orderId);
		if(order.isPresent()) return order.get();
		
		throw new OrderException("Order not found with the id "+orderId);
	}

	@Override
	public List<Order> getUserOrders(Long userId) throws OrderException {
		List<Order> orders=orderRepository.findAllUserOrders(userId);
		return orders;
	} 

	@Override
	public List<Order> getOrdersOfRestaurant(Long restaurantId) throws OrderException, RestaurantException {
		
			List<Order> orders = orderRepository.findOrdersByRestaurantId(restaurantId);
			return orders;
	}

	

}
