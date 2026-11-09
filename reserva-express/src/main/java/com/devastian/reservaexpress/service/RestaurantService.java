package com.devastian.reservaexpress.service;

import java.util.List;

import com.devastian.reservaexpress.dto.Response;
import com.devastian.reservaexpress.dto.RestaurantRequest;
import com.devastian.reservaexpress.entity.Restaurant;

public interface RestaurantService {
	
	Response<List<Restaurant>> getAllRestaurants();
	Response<Restaurant> getRestaurantById(Long id);
	Response<List<Restaurant>> getAllMyRestaurants();
	
	Response<Restaurant> createRestaurant(RestaurantRequest restaurantRequest);
	Response<Restaurant> updateRestaurant(Long id, RestaurantRequest restaurantRequest);
	
}
