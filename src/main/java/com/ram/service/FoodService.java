package com.ram.service;

import java.util.List;

import com.ram.Exception.FoodException;
import com.ram.Exception.RestaurantException;
import com.ram.model.Food;
import com.ram.model.Restaurant;
import com.ram.request.CreateFoodRequest;

public interface FoodService {

	public Food createFood(CreateFoodRequest req,
						   Restaurant restaurant) throws FoodException, RestaurantException;

	void deleteFood(Long foodId) throws FoodException;
	
	public List<Food> getRestaurantsFood(Long restaurantId) throws FoodException;
	
	public List<Food> searchFood(String keyword);
	
	public Food findFoodById(Long foodId) throws FoodException;

}
