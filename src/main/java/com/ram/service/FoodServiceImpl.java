package com.ram.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ram.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ram.Exception.FoodException;
import com.ram.Exception.RestaurantException;
import com.ram.model.Food;
import com.ram.model.Restaurant;
import com.ram.repository.foodRepository;
import com.ram.request.CreateFoodRequest;


@Service
public class FoodServiceImpl implements FoodService {
	@Autowired
	private foodRepository foodRepository;



	@Autowired
	private RestaurantRepository restaurantRepository;

	@Override
	public Food createFood(CreateFoodRequest  req,

						   Restaurant restaurant)
			throws FoodException,
	RestaurantException {

			Food food=new Food();
			food.setDescription(req.getDescription());

			food.setName(req.getName());
			food.setPrice((long) req.getPrice());

		food.setRestaurant(restaurant);
			food = foodRepository.save(food);

			restaurant.getFoods().add(food);
			return food;
		
	}

	@Override
	public void deleteFood(Long foodId) throws FoodException {
		Food food=findFoodById(foodId);
		food.setRestaurant(null);;
//		foodRepository.save(food);
		foodRepository.delete(food);

	}


	@Override
	public List<Food> getRestaurantsFood(
			Long restaurantId
			) throws FoodException {
		List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
		return foods;
		
	}




	@Override
	public List<Food> searchFood(String keyword) {
		List<Food> items=new ArrayList<>();
		
		if(keyword!="") {
			System.out.println("keyword -- "+keyword);
			items=foodRepository.searchByName(keyword);
		}
		
		return items;
	}


	@Override
	public Food findFoodById(Long foodId) throws FoodException {
		Optional<Food> food = foodRepository.findById(foodId);
		if (food.isPresent()) {
			return food.get();
		}
		throw new FoodException("food with id" + foodId + "not found");
	}

}
