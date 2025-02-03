package com.ram.controller;

import java.util.List;

import com.ram.Exception.CartException;
import com.ram.Exception.RestaurantException;
import com.ram.request.CreateOrderRequest;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ram.Exception.OrderException;
import com.ram.Exception.UserException;
import com.ram.model.Order;
import com.ram.model.User;
import com.ram.service.OrderService;
import com.ram.service.UserService;

@RestController
@RequestMapping("/api")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@PostMapping("/order")

	public ResponseEntity<String>  createOrder(@RequestBody CreateOrderRequest order,
														@RequestHeader("Authorization") String jwt)
			throws UserException, RestaurantException,
			CartException,
			OrderException{
		User user=userService.findUserProfileByJwt(jwt);
		System.out.println("req user "+user.getEmail());
		if(order!=null) {
			String res = orderService.createOrder(order,user);
			return ResponseEntity.ok(res);

		}else throw new OrderException("Please provide valid request body");

	}


	@GetMapping("/order/user")
    public ResponseEntity<List<Order>> getAllUserOrders(	@RequestHeader("Authorization") String jwt) throws OrderException, UserException{
    
    	User user=userService.findUserProfileByJwt(jwt);
    	
    	if(user.getId()!=null) {
    	  List<Order> userOrders = orderService.getUserOrders(user.getId());
    	  return ResponseEntity.ok(userOrders);
    	}
		else {
    		return new ResponseEntity<List<Order>>(HttpStatus.BAD_REQUEST);
    	}
    }
    

    

	
}
